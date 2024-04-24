const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:7001/ws'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    intervalId = setInterval(() => {
        stompClient.publish({
            destination: "/app/getExtractedSize",
            body: JSON.stringify({})
        });
    }, 1000);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
        console.log(JSON.parse(greeting.body).content)
    });
    stompClient.subscribe('/topic/extractedSize', (extractedSize) => {
        console.log(extractedSize.body)
        const extractedSizeData = JSON.parse(extractedSize.body).content;
        console.log(extractedSizeData); // Log dữ liệu ra console
        showExtractedSize(extractedSizeData); // Hiển thị dữ liệu lên giao diện
    });
};


stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
    $("#extractedSize").html("")
}
function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
    stompClient.publish({
        destination: "/app/unzip",
        body: JSON.stringify({})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}
function showExtractedSize(message) {
    $("#extractedSize").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});