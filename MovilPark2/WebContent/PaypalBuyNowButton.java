package ec.gob.movilpark.gestor;

import org.zkoss.zhtml.Form;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;

import ec.gob.movilpark.dto.Session;

public class PaypalBuyNowButton extends Button {
	
	private String _business = null;
    private String _itemName = "";
    private String _itemNumber = ""; 
    private String _amount = null;
    private String _noShipping = "0";
    private String _noNote = "0";
    private String _currencyCode = "USD";
    private String _notify = "http://40.76.59.162:8080/MovilPark/login";
    private String _return = "http://40.76.59.162:8080/MovilPark/login";
    private Session objSession;
  //generate form and submit
    public void onClick(MouseEvent event) {
    	
    	objSession=(Session) Executions.getCurrent().getSession().getAttribute("session");
		if(objSession==null)
			{
				Executions.getCurrent().sendRedirect("/Aplicacion/login.zul");
				return;
			}
    	
        //create paypal form
        Form form = new Form();
        form.setDynamicProperty("action", "https://www.paypal.com/cgi-bin/webscr");
        form.setDynamicProperty("method", "post");
        form.setPage(this.getPage());
         
        //cmd
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "cmd");
            input.setValue("_xclick");
        }
         
        //business
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "business");
            input.setValue(_business);
        }
         
        //item_name
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "item_name");
            input.setValue(_itemName);
        }
     
        //item_number
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "item_number");
            input.setValue(_itemNumber);
        }
         
        //amount
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "amount");
            input.setValue(_amount);
        }
         
        //no_shipping
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "no_shipping");
            input.setValue(_noShipping);
        }
     
        //no_note
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "no_note");
            input.setValue(_noNote);
        }
     
        //currency_code
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "currency_code");
            input.setValue(_currencyCode);
        }
         
        //bn
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "bn");
            input.setValue("PP-BuyNowBF");
        }
        
        //return
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "return");
            input.setValue(_return);
        }
        
        
      //notify
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "notify_url");
            input.setValue(_notify);
        }
        
      //style
        {
            Input input = new Input();
            input.setParent(form);
            input.setDynamicProperty("type", "hidden");
            input.setDynamicProperty("name", "style");
            input.setValue("background-color:green");
        }
         
        //submit the form
        Clients.submitForm(form);
    }

    
     
    //setters
    public void setBusiness(String str) {
        _business = str;
    }
     
    public void setItemName(String str) {
        _itemName = str;
    }
     
    public void setItemNumber(String str) {
        _itemNumber = str;
    }
 
    public void setAmount(String str) {
        _amount = str;
    }
     
    public void setNoShipping(String str) {
        _noShipping = str;
    }
     
    public void setNoNote(String str) {
        _noNote = str;
    }
     
    public void setCurrencyCode(String str) {
        _currencyCode = str;
    }
    

	public void setNotify(String _notify) {
		this._notify = _notify;
	}



	public void setReturn(String _return) {
		this._return = _return;
	}

}
