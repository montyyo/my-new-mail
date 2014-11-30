
public class MailClient
{
   //servidor- objeto mailserver
   private MailServer server;
   //usuario- direccion de correo del usuario que usa el cliente.
   private String user;
   //atributo ultimo mensaje
   private MailItem lastEmail;
   //contador mensajer enviados
   private int totalMail;
   //contador spam
   private int totalSpam;
   //enviados
   private int totalSend;
   //mayor mensaje
   private int longMail;
   //usuario mensaje mas largo
   private String longMailUser;
   /**
    * constructor con dos parametros
    */
   public MailClient(MailServer server, String user)
   {
       this.server = server;
       this.user = user;
       totalMail = 0;
       totalSpam = 0;
       totalSend = 0;
       longMail = 0;
   }
   
   /**
    * metodo getNextMailItem para recuperar un correo
    */
   public MailItem getNextMailItem()
   {
       lastEmail = server.getNextMailItem(user);
       return lastEmail;
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
       //String.contains("");
       String oferta = "oferta";
       String viagra = "viagra";
       String proyecto = "proyecto";
       lastEmail = server.getNextMailItem(user);
       String mail = lastEmail.getMessage();
       int largeMessage = mail.length();//nº de caracteres del mensaje
       
       if (lastEmail != null){
           if ( mail.contains(proyecto) || ( !(mail.contains(viagra) || mail.contains(oferta)))){
                lastEmail.printDatos();
                totalMail = totalMail + 1;
                if ( largeMessage > longMail) {
                    longMail = largeMessage;
                    String origen = lastEmail.getFrom();
                    longMailUser = origen;
                }
           }
           else{
                if (mail.contains(viagra) || mail.contains(oferta)){
                    System.out.println("spam");
                    totalSpam = totalSpam + 1;
                }
           }
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
     totalSend= totalSend +1;
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
        String oferta = "oferta";
       String viagra = "viagra";
       String proyecto = "proyecto";
       lastEmail = server.getNextMailItem(user);
       String mail = lastEmail.getMessage();
      //ultimo mensaje  del usuario, peticion de ultimo mensaje(vacia bandeja), por ello usamos la variable local en el if
      
      if (lastEmail != null){
          if ( mail.contains(proyecto) || ( !(mail.contains(viagra) || mail.contains(oferta)))){
          String destino = lastEmail.getFrom();//exraemos el destino del ultimo mensaje
          String subject = "RE:" + lastEmail.getSubject(); //reenvio del asunto
          String newMessage  = "Estoy de vacaciones" + "\n"+ "Ultimo mensaje enviado:" + "\n" + lastEmail.getMessage();
          MailItem autorespond = new MailItem(user, destino,subject,newMessage); //objeto para enviar correo autorrespuesta
          server.post(autorespond); //metodo para enviar mensajes
          totalMail = totalMail + 1;
          System.out.println("Su mensaje fue enviado.");
           }
           else{
                if (mail.contains(viagra) || mail.contains(oferta)){
                    System.out.println("spam");
                    totalSpam = totalSpam + 1;
                }
           }
          
      }
      else{
          System.out.println("No hay mensajes nuevos");
      }
      // server.post(lastmessage.printDatos());
      // " vacaciones" + System.lineSeparator() --> salto de linea 
    }
   
   /**
     * metodo para ver ultimo correo por pantalla siempre que queramos
     */
    public void printLastMailItem()
    {
        if (lastEmail != null){
           lastEmail.printDatos();
        }
        else{
             System.out.println("no hay mensajes guardados" );
        }
      
    }
    
    /**
     * metodo para realizar estadisticas
     */
   public void mailStadistics()
   {
     int howMany = server.howManyMailItems(user);
     int totalRecived = totalMail + totalSpam;
     
     
     System.out.println("mensajes enviados:  " + totalSend);
     System.out.println("total mensajes recividos:  " + totalRecived);
     if (totalSpam != 0){
     int porSpam = (totalSpam * 100) / totalRecived;
     System.out.println("Porcentaje de spam:  " + porSpam + "%");
     }
     else{
        System.out.println("Porcentaje de spam: 0 % ");
     }
     System.out.println("mensaje más largo:  " + longMail + "caracteres" + " desde:  " +longMailUser );
   }
}
   
