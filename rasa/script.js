document.addEventListener("DOMContentLoaded", function () {
    const chatbot = new RasaWebchat.default({
        socketUrl: "http://localhost:5005",  // URL of your Rasa server
        initPayload: "/get_started",  // Initial payload to send to the bot
        customData: { language: "pt" },  // Additional data to send to the bot
        title: "Chatbot",
        subtitle: "Olá, eu sou o assistente virtual das bibliotecas da Faccat!",
        displayUnreadCount: true,
        showFullScreenButton: true,
        hideWhenNotConnected: false,
        connectOn: "open",
    });

    chatbot.on("userInput", async (event) => {
        const userMessage = event.detail;
        const response = await fetch("http://localhost:5005/webhooks/rest/webhook", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ message: userMessage }),
        });
    
        const data = await response.json();
    
        // Display the user's message in the chat interface
        displayMessage(userMessage, "user");
    
        // Display Rasa's response in the chat interface
        data.forEach((message) => {
            displayMessage(message.text, "bot");
        });
    });    

    document.getElementById("send-button").addEventListener("click", function () {
        const userInput = document.getElementById("user-message").value;
        chatbot.send(userInput);
        document.getElementById("user-message").value = "";
    });

    function displayMessage(message, sender) {
        const messageContainer = document.getElementById("message-container");
        const messageElement = document.createElement("div");
        messageElement.classList.add("message");
        messageElement.classList.add(sender);
        messageElement.innerText = message;
        messageContainer.appendChild(messageElement);
    }
});

