
public class MailClient
{
   //servidor- objeto mailserver
   private MailServer server;
   //usuario- direccion de correo del usuario que usa el cliente.
   private String user;
   
   
   /**
    * constructor con dos parametros
    */
   public MailClient(MailServer server, String user)
   {
       this.server = server;
       this.user = user;
    
   }
   
   /**
    * metodo getNextMailItem para recuperar un correo
    */
   public MailItem getNextMailItem()
   {
       return server.getNextMailItem(user);
       //invocamos del servidor el metodo para obtener el ultimo correo
       //who que es el usuario del cual queremos obtener el correo
       // MailItem email = server.getNextMailItem(user); --->
   }
   
   /**
    * metodo que imprime el correo del servidor
    */
   public void printNextMailItem()
   {
       //variable local para guardar los resultados del metodo.
       MailItem newItem = server.getNextMailItem(user);
       if ( newItem !=null)
       {
           newItem.printDatos();
        
       }
       else
       {
            System.out.println("No hay mensajes nuevos en el buzón");
       }
   }
   
   /**
    * metodo que envia un email
    */
   public void sendMailItem(String newTo, String subject, String newMessage)
   {
     //creamos un objeto mailaitem, (new mailitem). 
     MailItem newmessage;
     newmessage = new MailItem(user, newTo, subject ,newMessage);
     server.post(newmessage);
     
   }
   
   /**
    * new method howManyMails, to know the number of email 
    */
   public void howManyMails()
   {
       // we call the method howManyMailItems in the class server to know it
       int howMany = server.howManyMailItems(user);
       System.out.println( "el numero de mensajes es:  "  + howMany);
   }
   
   /**
    * method with autorespond, send an automatic email when the user 
    * have holidays
    */
   public void getNextMailItemAndAutorespond()
   {
      MailItem lastmessage = server.getNextMailItem(user);//ultimo mensaje  
      String destino = lastmessage.getFrom();//exraemos el destino del ultimo mensaje
      String subject = "RE:" + lastmessage.getSubject(); //reenvio del asunto
      String newMessage  = "Estoy de vacaciones" + "\n"+ "Ultimo mensaje enviado:" + "\n" + lastmessage.getMessage();
      MailItem autorespond = new MailItem(user, destino,subject,newMessage); 
      server.post(autorespond); 
      
      System.out.println("Su mensaje fue enviado.");
            
      // server.post(lastmessage.printDatos());
    }
   
}
   
