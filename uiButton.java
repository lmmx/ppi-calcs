import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class uiButton
    extends JButton
{
	public int id;
	public String btn_text;
	
	public uiButton(String btn_text, int id){
		this.setText(btn_text);
		this.btn_text = btn_text;
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
}