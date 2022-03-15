package com.example.jspservice.tiles.parser;

import com.example.jspservice.dom.Node;
import com.example.jspservice.dom.Xml.XmlFileNode;
import com.example.jspservice.tiles.model.TilesAttribute;
import com.example.jspservice.tiles.model.TilesDefinition;
import com.example.jspservice.utils.Helper.TilesHelper;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tiles.Definition;
import org.apache.tiles.Expression;
import org.apache.tiles.ListAttribute;
import org.apache.tiles.definition.DefinitionsFactoryException;
import org.apache.tiles.definition.DefinitionsReader;
import org.apache.tiles.definition.digester.DigesterDefinitionsReaderException;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cuong on 5/17/2017.
 */
public class JciaDigesterDefinitionsReader implements DefinitionsReader {

    private static final Logger logger = LogManager.getLogger(JciaDigesterDefinitionsReader.class);

    /**
     * Digester validation parameter name.
     */
    public static final String PARSER_VALIDATE_PARAMETER_NAME =
            "org.apache.tiles.definition.digester.DigesterDefinitionsReader.PARSER_VALIDATE";

    // Digester rules constants for tag interception.

    /**
     * Intercepts a &lt;definition&gt; tag.
     */
    private static final String DEFINITION_TAG = "tiles-definitions/definition";

    /**
     * Intercepts a &lt;put-attribute&gt; tag.
     */
    private static final String PUT_TAG = "*/definition/put-attribute";

    /**
     * Intercepts a &lt;definition&gt; inside a  &lt;put-attribute&gt; tag.
     */
    private static final String PUT_DEFINITION_TAG = "*/put-attribute/definition";

    /**
     * Intercepts a &lt;definition&gt; inside an &lt;add-attribute&gt; tag.
     */
    private static final String ADD_DEFINITION_TAG = "*/add-attribute/definition";

    /**
     * Intercepts a &lt;put-list-attribute&gt; tag inside a %lt;definition&gt;
     * tag.
     */
    private static final String DEF_LIST_TAG = "*/definition/put-list-attribute";

    /**
     * Intercepts a &lt;add-attribute&gt; tag.
     */
    private static final String ADD_LIST_ELE_TAG = "*/add-attribute";

    /**
     * Intercepts a &lt;add-list-attribute&gt; tag.
     */
    private static final String NESTED_LIST = "*/add-list-attribute";

    // Handler class names.

    /**
     * The handler to create definitions.
     *
     * @since 2.1.0
     */
    protected static final String DEFINITION_HANDLER_CLASS =
            TilesDefinition.class.getName();

    /**
     * The handler to create attributes.
     *
     * @since 2.1.0
     */
    protected static final String PUT_ATTRIBUTE_HANDLER_CLASS =
            TilesAttribute.class.getName();

    /**
     * The handler to create list attributes.
     *
     * @since 2.1.0
     */
    protected static final String LIST_HANDLER_CLASS =
            ListAttribute.class.getName();

    /**
     * XmlFileNode indicating for this tiles resource
     */
    protected XmlFileNode resourceNode;

    public class JciaRule extends Rule {

        public void begin(String namespace, String name, Attributes attributes, JciaLocator locator) throws Exception {
            super.begin(namespace, name, attributes);
        }
    }

    /**
     * Digester rule to manage definition filling.
     *
     * @since 2.1.2
     */
    public class FillDefinitionRule extends JciaRule {

        /** {@inheritDoc} */
        @Override
        public void begin(String namespace, String name, Attributes attributes, JciaLocator locator) {
            TilesDefinition definition = (TilesDefinition) digester.peek();
            definition.setName(attributes.getValue("name"));
            definition.setPreparer(attributes.getValue("preparer"));
            String extendsAttribute = attributes.getValue("extends");
            definition.setExtends(extendsAttribute);

            String template = attributes.getValue("template");
            TilesAttribute attribute = TilesAttribute.createTemplateAttribute(template);
            attribute.setExpressionObject(Expression
                    .createExpressionFromDescribedExpression(attributes
                            .getValue("templateExpression")));
            attribute.setRole(attributes.getValue("role"));
            String templateType = attributes.getValue("templateType");
            if (templateType != null) {
                attribute.setRenderer(templateType);
            } else if (extendsAttribute != null && templateType == null) {
                attribute.setRenderer(null);
            }

            definition.setTemplateAttribute(attribute);
            Node defNode = TilesHelper.getTagNodeByLocation(
                    JciaDigesterDefinitionsReader.this.resourceNode, locator);
            if (defNode != null) {
                definition.setTreeNode(defNode);
            }
        }
    }

    /**
     * Digester rule to manage attribute filling.
     *
     * @since 2.1.0
     */
    public class FillAttributeRule extends JciaRule {

        /** {@inheritDoc} */
        @Override
        public void begin(String namespace, String name, Attributes attributes, JciaLocator locator) {
            TilesAttribute attribute = (TilesAttribute) digester.peek();
            attribute.setValue(attributes.getValue("value"));
            String expression = attributes.getValue("expression");
            attribute.setExpressionObject(Expression
                    .createExpressionFromDescribedExpression(expression));
            attribute.setRole(attributes.getValue("role"));
            attribute.setRenderer(attributes.getValue("type"));

            Node attrNode = TilesHelper.getTagNodeByLocation(
                    JciaDigesterDefinitionsReader.this.resourceNode, locator);
            if (attrNode != null) {
                attribute.setTreeNode(attrNode);
            }

            TilesDefinition definition = (TilesDefinition) digester.peek(1);
            definition.addTilesAttribute(attribute);
        }
    }

    /**
     * Digester rule to manage assignment of the attribute to the parent
     * element.
     *
     * @since 2.1.0
     */
    public class PutAttributeRule extends JciaRule {

        /** {@inheritDoc} */
        @Override
        public void begin(String namespace, String name, Attributes attributes, JciaLocator locator) {
            TilesAttribute attribute = (TilesAttribute) digester.peek(0);
            TilesDefinition definition = (TilesDefinition) digester.peek(1);
            definition.putAttribute(attributes.getValue("name"), attribute,
                    "true".equals(attributes.getValue("cascade")));
        }
    }

    /**
     * Digester rule to manage assignment of a nested definition in an attribute
     * value.
     *
     * @since 2.1.0
     */
    public class AddNestedDefinitionRule extends JciaRule {

        /** {@inheritDoc} */
        @Override
        public void begin(String namespace, String name, Attributes attributes, JciaLocator locator) {
            TilesDefinition definition = (TilesDefinition) digester.peek(0);
            if (definition.getName() == null) {
                definition.setName(getNextUniqueDefinitionName(definitions));
            }
            TilesAttribute attribute = (TilesAttribute) digester.peek(1);
            attribute.setValue(definition.getName());
            attribute.setRenderer("definition");
        }
    }

    /**
     * <code>Digester</code> object used to read TilesDefinition data
     * from the source.
     */
    protected Digester digester;

    /**
     * The set of public identifiers, and corresponding resource names for
     * the versions of the configuration file DTDs we know about.  There
     * <strong>MUST</strong> be an even number of Strings in this list!
     */
    protected String[] registrations;

    /**
     * Stores TilesDefinition objects.
     */
    private Map<String, TilesDefinition> definitions;

    /**
     * Index to be used to create unique definition names for anonymous
     * (nested) definitions.
     */
    private int anonymousDefinitionIndex = 1;

    /**
     * Creates a new instance of DigesterDefinitionsReader.
     */
    public JciaDigesterDefinitionsReader() {
        digester = new JciaDigester();
        digester.setNamespaceAware(true);
        digester.setUseContextClassLoader(true);
        digester.setErrorHandler(new ThrowingErrorHandler());

        // Register our local copy of the DTDs that we can find
        String[] registrations = getRegistrations();
        for (int i = 0; i < registrations.length; i += 2) {
            URL url = this.getClass().getResource(
                    registrations[i + 1]);
            if (url != null) {
                digester.register(registrations[i], url.toString());
            }
        }

        initSyntax(digester);
    }

    /**
     * Sets the validation of XML files.
     *
     * @param validating <code>true</code> means that XML validation is turned
     * on. <code>false</code> otherwise.
     * @since 3.3.0
     */
    public void setValidating(boolean validating) {
        digester.setValidating(validating);
    }

    public Map<String, TilesDefinition> read2(XmlFileNode resourceNode) throws IOException {
        // This is an instance variable instead of a local variable because
        // we want to be able to call the addDefinition method to populate it.
        // But we reset the Map here, which, of course, has threading implications.
        definitions = new LinkedHashMap<String, TilesDefinition>();

        if (resourceNode == null) {
            // Perhaps we should throw an exception here.
            return null;
        }

        this.resourceNode = resourceNode;

        File xmlFile = new File(resourceNode.getAbsolutePath());
        InputStream input = new FileInputStream(xmlFile);

        try {
            // set first object in stack
            //digester.clear();
            digester.push(this);
            // parse
            digester.parse(input);

        } catch (SAXException e) {
            throw new DefinitionsFactoryException(
                    "XML error reading definitions.", e);
        } catch (IOException e) {
            throw new DefinitionsFactoryException(
                    "I/O Error reading definitions.", e);
        } finally {
            digester.clear();
            input.close();
        }

        return definitions;
    }

    @Deprecated
    @Override
    public Map<String, Definition> read(Object source) {
        return null;
    }

    /**
     * Initialised the syntax for reading XML files containing Tiles
     * definitions.
     *
     * @param digester The digester to initialize.
     */
    protected void initSyntax(Digester digester) {
        initDigesterForTilesDefinitionsSyntax(digester);
    }


    /**
     * Init digester for Tiles syntax with first element = tiles-definitions.
     *
     * @param digester Digester instance to use.
     */
    private void initDigesterForTilesDefinitionsSyntax(Digester digester) {
        // syntax rules
        digester.addObjectCreate(DEFINITION_TAG, DEFINITION_HANDLER_CLASS);
        digester.addRule(DEFINITION_TAG, new FillDefinitionRule());
        digester.addSetNext(DEFINITION_TAG, "addDefinition", DEFINITION_HANDLER_CLASS);

        // nested definition rules
        digester.addObjectCreate(PUT_DEFINITION_TAG, DEFINITION_HANDLER_CLASS);
        digester.addRule(PUT_DEFINITION_TAG, new FillDefinitionRule());
        digester.addSetRoot(PUT_DEFINITION_TAG, "addDefinition");
        digester.addRule(PUT_DEFINITION_TAG, new AddNestedDefinitionRule());
        digester.addObjectCreate(ADD_DEFINITION_TAG, DEFINITION_HANDLER_CLASS);
        digester.addRule(ADD_DEFINITION_TAG, new FillDefinitionRule());
        digester.addSetRoot(ADD_DEFINITION_TAG, "addDefinition");
        digester.addRule(ADD_DEFINITION_TAG, new AddNestedDefinitionRule());

        // put / putAttribute rules
        // Rules for a same pattern are called in order, but rule.end() are called
        // in reverse order.
        // SetNext and CallMethod use rule.end() method. So, placing SetNext in
        // first position ensure it will be called last (sic).
        digester.addObjectCreate(PUT_TAG, PUT_ATTRIBUTE_HANDLER_CLASS);
        digester.addRule(PUT_TAG, new FillAttributeRule());
        digester.addRule(PUT_TAG, new PutAttributeRule());
        // TilesDefinition level list rules
        // This is rules for lists nested in a definition
        digester.addObjectCreate(DEF_LIST_TAG, LIST_HANDLER_CLASS);
        digester.addSetProperties(DEF_LIST_TAG);
        digester.addRule(DEF_LIST_TAG, new PutAttributeRule());
        // list elements rules
        // We use TilesAttribute class to avoid rewriting a new class.
        // Name part can't be used in listElement attribute.
        digester.addObjectCreate(ADD_LIST_ELE_TAG, PUT_ATTRIBUTE_HANDLER_CLASS);
        digester.addRule(ADD_LIST_ELE_TAG, new FillAttributeRule());
        digester.addSetNext(ADD_LIST_ELE_TAG, "add", PUT_ATTRIBUTE_HANDLER_CLASS);

        // nested list elements rules
        // Create a list handler, and add it to parent list
        digester.addObjectCreate(NESTED_LIST, LIST_HANDLER_CLASS);
        digester.addSetProperties(NESTED_LIST);
        digester.addSetNext(NESTED_LIST, "add", PUT_ATTRIBUTE_HANDLER_CLASS);
    }

    /**
     * Adds a new <code>TilesDefinition</code> to the internal Map or replaces
     * an existing one.
     *
     * @param definition The TilesDefinition object to be added.
     */
    public void addDefinition(TilesDefinition definition) {
        String name = definition.getName();

        if (name == null) {
            throw new DigesterDefinitionsReaderException(
                    "A root definition has been defined with no name");
        }

        definitions.put(name, definition);
    }

    /**
     * Error Handler that throws every exception it receives.
     */
    private static class ThrowingErrorHandler implements ErrorHandler {

        /** {@inheritDoc} */
        public void warning(SAXParseException exception) throws SAXException {
            throw exception;
        }

        /** {@inheritDoc} */
        public void error(SAXParseException exception) throws SAXException {
            throw exception;
        }

        /** {@inheritDoc} */
        public void fatalError(SAXParseException exception) throws SAXException {
            throw exception;
        }
    }

    /**
     * Returns the registrations for local DTDs.
     *
     * @return An array containing the locations for registrations of local
     * DTDs.
     * @since 2.1.0
     */
    protected String[] getRegistrations() {
        if (registrations == null) {
            registrations = new String[] {
                    "-//Apache Software Foundation//DTD Tiles Configuration 3.0//EN",
                    "/org/apache/tiles/resources/tiles-config_3_0.dtd"};
        }
        return registrations;
    }

    /**
     * Create a unique definition name usable to store anonymous definitions.
     *
     * @param definitions The already created definitions.
     * @return The unique definition name to be used to store the definition.
     * @since 2.1.0
     */
    protected String getNextUniqueDefinitionName(
            Map<String, TilesDefinition> definitions) {
        String candidate;

        do {
            candidate = "$anonymousDefinition" + anonymousDefinitionIndex;
            anonymousDefinitionIndex++;
        } while (definitions.containsKey(candidate));

        return candidate;
    }
}
