package edu.bbte.idde.frim1910.reactivefrim1910.messaging;

import lombok.Data;

@Data
public class Message {
    private String text;

    public static Message fromString(String text) {
        Message message = new Message();
        message.setText(text);
        return message;
    }
}
