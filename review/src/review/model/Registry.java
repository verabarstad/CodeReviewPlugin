package review.model;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import review.utils.Common;

public class Registry implements Serializable
{
	private static final long serialVersionUID = -4857894436139319777L;
    private static final Registry instance = new Registry();
 
    private HashMap registry = new HashMap();
 
    private Registry ()
    { }
 
    public static Registry getInstance ()
    {
        return instance;
    }
    public static Registry getRegistry()
    {
    	 Registry reg;
    	  try
          {
              FileInputStream fis = new FileInputStream(Common.getCodeReviewPath() + "\\registry.ser");
              ObjectInputStream ois = new ObjectInputStream(fis);
   
              reg = (Registry) ois.readObject();
              ois.close();
              
          }
          catch (Exception e)
          {
        	  //file not found 
        	
              //e.printStackTrace();
              reg = new Registry();
          }
		return reg;
    }
 
     public  void saveRegistry(Registry reg) {
    	 try
         {
             FileOutputStream fos = new FileOutputStream (Common.getCodeReviewPath() + "\\registry.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);
  
             oos.writeObject(reg);
  
             oos.flush();
             oos.close();
         }
         catch (IOException e)
         {
             e.printStackTrace();
         }
     }
     
    public void addObject (String k, Object v)
    {
        this.registry.put (k, v);
    }
 
    public Object getObject (String k)
    {
        return this.registry.get (k);
    }
    public Set getEntrySet ()
    {
    	return this.registry.entrySet();
    }
    
    }


