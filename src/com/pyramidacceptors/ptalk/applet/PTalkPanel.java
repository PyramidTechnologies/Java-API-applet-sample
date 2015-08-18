/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pyramidacceptors.ptalk.applet;

import com.pyramidacceptors.ptalk.api.BillNames;
import com.pyramidacceptors.ptalk.api.PyramidAcceptor;
import com.pyramidacceptors.ptalk.api.PyramidDeviceException;
import com.pyramidacceptors.ptalk.api.event.CreditEvent;
import com.pyramidacceptors.ptalk.api.event.PTalkEvent;
import com.pyramidacceptors.ptalk.api.event.PTalkEventListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.jsoup.Jsoup;

/**
 *
 * @author Cory Todd <cory@pyramidacceptors.com>
 */
public class PTalkPanel extends JPanel implements Observer, PTalkEventListener {

    private Thread timerThread;
    private PTalkTimer timer = new PTalkTimer();
    private PyramidAcceptor acceptor;
    private boolean isAnalyzing = false;

    public PTalkPanel() {
                /* Create and display the applet */
        try {
            initComponents();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.timerString.setText("Error initializing Applet");
        }
        
        try {
            
            // Create instance of bill acceptor
            acceptor = PyramidAcceptor.valueOfRS232();
            // Connect! this handles all the handsahking
            acceptor.connect();
            
            // Start our sample timer - in this sample we're buying time            
            // sample timer callback
            timer.addObserver(this);
            
            // acceptor callback - handle credit events
            acceptor.addChangeListener(this);
            
            // Sample timer runs on its own thread
            timerThread = new Thread(timer);
            timerThread.start();

        } catch (PyramidDeviceException ex) {
            Logger.getLogger(PTalkPanel.class.getName()).log(Level.SEVERE, null, ex);
            this.timerString.setText("Error connecting to Pyramid Acceptor");
        }

    }   
    
    /**
     * Disconnect the slave device
     */
    public void close() {
        this.acceptor.disconnect();
    }

    @Override
    public void changeEventReceived(PTalkEvent evt) {
        if (evt instanceof CreditEvent) {
            BillNames bn = evt.getBillName();
            switch(evt.getBillName())
            {
                case Bill1:
                    timer.addTime(1);
                    break;
                case Bill2:
                    timer.addTime(2);
                    break;
                case Bill3:
                    timer.addTime(6);                    
                    break;
                case Bill4:
                    timer.addTime(15);                    
                    break;
                case Bill5:
                   timer.addTime(30);
                   break;
                case Bill6:
                    timer.addTime(60);                    
                    break;
                case Bill7:
                    timer.addTime(1000);                        
                    break;                                    
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == timer) {
            this.timerString.setText(timer.getValue());
            if (timer.outOfTime) {
                this.btnAnalyze.setEnabled(false);
            } else {
                this.btnAnalyze.setEnabled(true);
            }

        }
    }
    class PTalkTimer extends Observable implements Runnable {

        final String timerString = "%s years, %s hours, %s days, %s minutes, "
                + "%s seconds until you are out of time";
        final String outOfStime = "Out of time! Please insert more money\r\n"
                + "1$ a minute or $5 for 6 minutes.";
        Calendar end;
        private String message = "";
        private boolean _stopThread = false;
        private boolean outOfTime = false;

        // Constructor
        PTalkTimer() {
            end = new GregorianCalendar();
            end.add(Calendar.SECOND, 60);
        }

        void addTime(int minutes) {
            // Start the clock over if we're out of time and the customer has paid
            if(outOfTime)
                end = new GregorianCalendar();
            end.add(Calendar.MINUTE, minutes);
        }

        public String getValue() {
            return this.message;
        }

        public void run() {

            while (!_stopThread) {
                long now = new GregorianCalendar().getTimeInMillis();
                long endMS = end.getTimeInMillis();

                if (endMS > now) {
                    outOfTime = false;
                    // Calculate difference in dates
                    long diff = endMS - now;

                    // Divide by 1000 to find number of seconds difference
                    diff = diff / 1000;

                    // Get seconds
                    int seconds = (int) diff % 60;

                    // Get minutes
                    diff = diff / 60;
                    int minutes = (int) diff % 60;

                    // Get hours
                    diff = diff / 60;
                    int hours = (int) diff % 24;

                    // Get days
                    diff = diff / 24;
                    int days = (int) diff % 365;

                    // Get days
                    diff = diff / 365;
                    int years = (int) diff;

                    // Generate date string
                    message = String.format(timerString, years, days,
                            hours, minutes, seconds);

                } else {
                    outOfTime = true;
                    message = outOfStime;
                }

                // Notify observers
                setChanged();
                notifyObservers();

                try {
                    // Sleep for a second
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Logger.getLogger("Timer").log(Level.WARNING, "Thread interrupted");
                }
            }
        }
    }

    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        timerString = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        targetUrl = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnAnalyze = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        analysisText = new javax.swing.JTextArea();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timerString, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timerString, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, 590, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Target URL");

        btnAnalyze.setText("Analyze");
        btnAnalyze.setEnabled(false);
        btnAnalyze.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnalyzeMouseClicked(evt);
            }
        });
        btnAnalyze.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAnalyzeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(targetUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAnalyze, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(targetUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnalyze))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        analysisText.setEditable(false);
        analysisText.setColumns(20);
        analysisText.setRows(5);
        jScrollPane1.setViewportView(analysisText);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 111, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnAnalyzeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnalyzeMouseClicked
        if(!isAnalyzing)
            analyze();
    }//GEN-LAST:event_btnAnalyzeMouseClicked

    private void btnAnalyzeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAnalyzeKeyPressed
       if(!isAnalyzing && (evt.getKeyCode() == KeyEvent.VK_ENTER || 
               evt.getKeyCode() == KeyEvent.VK_SPACE)) {
           analyze();
       }
    }//GEN-LAST:event_btnAnalyzeKeyPressed

    private void analyze() {
        isAnalyzing = true;
        URL url;
        HashMap<String, Integer> analysis = new HashMap<>();
        ValueComparator bvc =  new ValueComparator(analysis);
        TreeMap<String,Integer> sorted = new TreeMap<>(bvc);
        try {
            // get URL content
            url = new URL(this.targetUrl.getText());
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;

            // Analyze
            while ((inputLine = br.readLine()) != null) {
                // Strip HTML
                inputLine = html2text(inputLine);
                // Split at any white space
                String[] words = inputLine.split("\\s+");
                for(String w : words)
                    if(!w.equals("")){
                        if(analysis.get(w) == null)
                            analysis.put(w, 1);
                        else
                            analysis.put(w, analysis.get(w)+1);
                    }
            }

            br.close();
            sorted.putAll(analysis);
            
            Iterator it = sorted.entrySet().iterator();
            while(it.hasNext()) {
                analysisText.append(it.next().toString() + "\n");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        isAnalyzing = false;
    }
    
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
    
    class ValueComparator implements Comparator<String> {

        Map<String, Integer> base;
        public ValueComparator(Map<String, Integer> base) {
            this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea analysisText;
    private javax.swing.JButton btnAnalyze;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField targetUrl;
    private javax.swing.JLabel timerString;
    // End of variables declaration//GEN-END:variables
}
