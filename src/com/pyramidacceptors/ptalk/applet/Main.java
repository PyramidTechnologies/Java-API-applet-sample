

package com.pyramidacceptors.ptalk.applet;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author cory
 */
public class Main extends JApplet {

    private static JApplet applet;
    PTalkPanel form;
    
    static JApplet getApplet() {
        return applet;
    }

    @Override
    public void init() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    form = new PTalkPanel();
                    setSize(580,400);                            
                    add(form);
                    applet = Main.this;
                }
            });
        }
        catch (Exception e) {
            //Do nothing
        }
    }
    
    @Override
    public void destroy() {
       form.close();
       applet.stop();
    }
}
