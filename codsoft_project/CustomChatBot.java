import java.util.Scanner;

public class CustomChatBot {

    public static void main(String[] args) {
        Scanner userinputScanner = new Scanner(System.in);
        System.out.println("Welcome! I'm here to chat with you. Type '0' if you want to end the conversation.");

        while (true) {
            System.out.print("User: ");
            String userMessage = userinputScanner.nextLine().toLowerCase();

            if (userMessage.contains("0")) {
                System.out.println("Bot: It was nice talking to you! Take care!");
                break;
            } else if (userMessage.contains("hello") || userMessage.contains("hi") || userMessage.contains("hey...Bot")) {
                System.out.println("Bot: Hey there! How can I help you today?");
            } else if (userMessage.contains("how are you") || userMessage.contains("how's it going")) {
                System.out.println("Bot: I'm just a chatbot, but I'm here to help! How about you?");
            } else if (userMessage.contains("your name")) {
                System.out.println("Bot: I'm your assistant chatbot I don't have any name. Call me whatever you like!");
            } else if (userMessage.contains("time") || userMessage.contains("date")) {
                System.out.println("Bot: I can't check the exact time, but I’m here anytime you need!");
            } else if (userMessage.contains("help") || userMessage.contains("assist") || userMessage.contains("support")) {
                System.out.println("Bot: I'm here to help! Let me know what type of help do ypu need.");
            } else if (userMessage.contains("what is your favorite movie") || userMessage.contains("what is your favorite color")) {
                System.out.println("Bot: I am a chatbot I don't have any personal feelings.. ");
            }else if (userMessage.contains("thank you") || userMessage.contains("thanks")) {
                System.out.println("Bot: You're very welcome!"); 
            }
            else {
                System.out.println("Bot: Hmm, I didn't catch that. Could you rephrase?");
            }
        }

        userinputScanner.close();
    }
}