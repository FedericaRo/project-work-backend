// package com.generation.progetto_finale.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.generation.progetto_finale.websocket.WebSocketService;


// @RestController
// public class ProvaSocketController 
// {
//     @Autowired
//     WebSocketService wServ;

//     @GetMapping("/prova")
//     public void getMethodName() 
//     {
//         wServ.sendMessage("calza", "una stringa, quello che vuoi");
//     }

//     @MessageMapping("/ricevitore")
//     @SendTo("/topic/calza")
//     public String messaggistica(String messaggino)
//     {
//         System.out.println(messaggino);
//         return "stringa che ritorna";
//     }
    
    
// }
