/* =========================================================
 * ====                   WARNING                        ===
 * =========================================================
 * The code in this tab has been generated from the GUI form
 * designer and care should be taken when editing this file.
 * Only add/edit code inside the event handlers i.e. only
 * use lines between the matching comment tags. e.g.

 void myBtnEvents(GButton button) { //_CODE_:button1:12356:
     // It is safe to enter your event code here  
 } //_CODE_:button1:12356:
 
 * Do not rename this tab!
 * =========================================================
 */

public void buttonNew_click(GButton source, GEvent event) { //_CODE_:buttonNew:677724:
  println("buttonNew clicked");
  newSettingWizard();
} //_CODE_:buttonNew:677724:

public void buttonLoad_click(GButton source, GEvent event) { //_CODE_:buttonLoad:917085:
  println("buttonLoad clicked");
   loadSettingWizard();
} //_CODE_:buttonLoad:917085:

public void buttonVoice_click(GButton source, GEvent event) { //_CODE_:buttonVoice:220763:
  println("buttonVoice clicked");
  voiceSetting();
} //_CODE_:buttonVoice:220763:

public void buttonSD_click(GButton source, GEvent event) { //_CODE_:buttonSD:263848:
  println("buttonSD clicked");
  createWriteToSDWindow();
} //_CODE_:buttonSD:263848:

public void buttonExit_click(GButton source, GEvent event) { //_CODE_:buttonExit:988049:
  println("buttonExit clicked");
  buttonExit();
} //_CODE_:buttonExit:988049:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(8);
  G4P.setCursor(ARROW);
  surface.setTitle("Configurator");
  buttonNew = new GButton(this, 20, 30, 100, 30);
  buttonNew.setText("New Setting");
  buttonNew.addEventHandler(this, "buttonNew_click");
  buttonLoad = new GButton(this, 20, 80, 100, 30);
  buttonLoad.setText("Load Setting");
  buttonLoad.addEventHandler(this, "buttonLoad_click");
  buttonVoice = new GButton(this, 230, 30, 100, 30);
  buttonVoice.setText("Voice Tuning");
  buttonVoice.addEventHandler(this, "buttonVoice_click");
  buttonSD = new GButton(this, 230, 80, 100, 30);
  buttonSD.setText("Write To Sd Card");
  buttonSD.addEventHandler(this, "buttonSD_click");
  buttonExit = new GButton(this, 20, 130, 100, 30);
  buttonExit.setText("Exit");
  buttonExit.addEventHandler(this, "buttonExit_click");
}

// Variable declarations 
// autogenerated do not edit
GButton buttonNew; 
GButton buttonLoad; 
GButton buttonVoice; 
GButton buttonSD; 
GButton buttonExit; 