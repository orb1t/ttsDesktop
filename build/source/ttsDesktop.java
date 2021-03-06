import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import g4p_controls.*; 
import java.awt.Font; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ttsDesktop extends PApplet {

// Need G4P library



//public variable
String voicePitch, voiceRate, voiceVolume, newSettingFilePath, loadSettingPath;
StringList textIndoor, textOutdoor;

public void setup() {
  newSettingFilePath = sketchPath() +"/data/my.cfg";
  loadSettingPath = "";
  textIndoor = new StringList();
  textOutdoor = new StringList();
  loadCurrentFont();
  
  createGUI();
  customGUI();
  // Place your setup code here
  println(sketchPath());
  loadVoiceSetting();
}
public void draw() {
  background(240);
  noStroke();

  //border bawah
  fill(255, 200);
  rect(0, height-20, width, 20);
  rect(0, 0, width, 20);

  //text
  fill(0);
  textFont(ubu12);
  text("Config Apps", width/2-35, 15);
  textSize(10);
  text("copyright \u00a9 2016 GTI", width/1.5f, height-5);
}

// Use this method to add additional statements
// to customise the GUI controls
public void customGUI() {
  buttonNew.setFont(GuiUbu11);
  buttonLoad.setFont(GuiUbu11);
  buttonVoice.setFont(GuiUbu11);
  buttonSD.setFont(GuiUbu11);
  buttonExit.setFont(GuiUbu11);
}

//handler window
int korNum = 0, slideNum = 0, newSetSlideNum = 0;
koridor[] myKoridor;
synchronized public void win_drawNewSetting(PApplet appc, GWinData data) {
  appc.background(240);
  appc.noStroke();

  //border
  appc.fill(255, 255, 255, 200);
  appc.rect(0, 0, appc.width, appc.height/7);
  appc.rect(0, appc.height-appc.height/7, appc.width, appc.height/7);

  //text
  appc.fill(0);
  appc.textFont(ubu14);
  appc.text("Welcome to the new setting wizard", 20, 30);
  appc.textSize(11);

  if (newSetSlideNum == 0) {
    appc.text("Input total koridor :", 10, 60);
  } else if (newSetSlideNum == 1) {
    appc.text("Input nama koridor " +slideNum, 10, 60);
    appc.text("Total halte (jalur pergi) == Total halte (jalur pulang) ?", 10, 110);
    if (muncul) {
      appc.text("input total halte (jalur pergi)", 10, 130);
      appc.text("input total halte (jalur pulang)", 10, 180);
    } else
      appc.text("input total halte (jalur pergi) dan (jalur pulang)", 10, 130);
  } else if (newSetSlideNum == 2) {
    appc.text("Input text indoor ", 10, 60);
  } else if (newSetSlideNum == 3) {
    appc.text("Input text outdoor ", 10, 60);
    if (browsed)
      appc.text("save path: " +newSettingFilePath, 10, newSettingWindow.height-50);
    else if (!browsed)
      appc.text("save path: " +newSettingFilePath, 10, newSettingWindow.height-50);
  }
}

synchronized public void newHalteWindowHandler(PApplet appc, GWinData data) {
  appc.background(240);
  appc.noStroke();

  //border
  appc.fill(255, 255, 255, 200);
  appc.rect(appc.width - 120, 0, appc.width - (appc.width - 120), appc.height);
}

synchronized public void newWriteToSDWindowHandler(PApplet appc, GWinData data) {
  appc.background(240);
  appc.noStroke();

  //border
  appc.fill(255, 255, 255, 200);
  appc.rect(0, appc.height-20, appc.width, appc.height/6);

  //text
  appc.fill(0);
  textFont(ubu11);
  appc.text("no setting load", 10, 20);
}

synchronized public void win_drawVoiceSetting(PApplet appc, GWinData data) {
  appc.background(240);
  appc.noStroke();

  //border
  appc.fill(255, 255, 255, 200);
  appc.rect(0, appc.height-20, appc.width, appc.height/6);

  //text
  appc.fill(0);
  textFont(ubu11);
  appc.text("Pitch (0 - 1) :", inputPitch.getX()-80, inputPitch.getY()+15);
  appc.text("Pitch (0 - 1) :", inputRate.getX()-80, inputPitch.getY()+15);
  appc.text("Pitch (0 - 1) :", inputVolume.getX()-80, inputPitch.getY()+15);
  appc.textSize(10);
  appc.text(sketchPath() +"/data/voice.cfg", 0, appc.height-7);
}
public void GUINewSetSlideNum0() {
  if (buttonNext != null && buttonBack != null && buttonContinue != null && optYes != null && optNo != null && in1 != null && in2 != null && editHalte != null) {
    buttonNext.setVisible(false);
    buttonBack.setVisible(false);
    buttonContinue.setVisible(true);
    optYes.setVisible(false);
    optNo.setVisible(false);
    in1.setVisible(false);
    in2.setVisible(false);
    editHalte.setVisible(false);
    for (int i=0; i<5; i++)
      inB[i].setVisible(false);
    buttonBrowse.setVisible(false);
  }
}
boolean muncul = false;
public void GUINewSetSlideNum1() {
  if (buttonNext != null && buttonBack != null && buttonContinue != null && optYes != null && optNo != null && in1 != null && in2 != null && editHalte != null) {
    buttonNext.setVisible(true);
    buttonBack.setVisible(true);
    buttonContinue.setVisible(false);
    optYes.setVisible(true);
    optNo.setVisible(true);
    if (in2.isVisible())
      muncul = true;
    else if (!in2.isVisible())
      muncul = false;
    for (int i=0; i<5; i++)
      inB[i].setVisible(false);
    buttonBrowse.setVisible(false);
  }
}
public void GUINewSetSlideNum2() {
  if (buttonNext != null && buttonBack != null && buttonContinue != null && optYes != null && optNo != null && in1 != null && in2 != null && editHalte != null) {
    buttonNext.setVisible(true);
    buttonBack.setVisible(true);
    buttonContinue.setVisible(false);
    optYes.setVisible(false);
    optNo.setVisible(false);
    in1.setVisible(false);
    editHalte.setVisible(false);
    for (int i=0; i<5; i++) {
      inB[i].setVisible(true);
      if (i==0)
        inB[i].setPromptText("input text indoor " +i);
      if (i>0)
        inB[i].setPromptText("input text indoor "+i +" (optional)");
    }
    buttonBrowse.setVisible(false);
  }
}
public void GUINewSetSlideNum3() {
  if (buttonNext != null && buttonBack != null && buttonContinue != null && optYes != null && optNo != null && in1 != null && in2 != null && editHalte != null) {
    buttonNext.setVisible(true);
    buttonBack.setVisible(true);
    buttonContinue.setVisible(false);
    optYes.setVisible(false);
    optNo.setVisible(false);
    in1.setVisible(false);
    editHalte.setVisible(false);
    for (int i=0; i<5; i++) {
      inB[i].setVisible(true);
      if (i==0)
        inB[i].setPromptText("input text outdoor " +i);
      if (i>0)
        inB[i].setPromptText("input text outdoor "+i +" (optional)");
    }
    buttonBrowse.setVisible(true);
  }
}
PFont linuxFont12, linuxFont11, linuxFont14;
PFont s11, s12, s14;
PFont ubu11, ubu12, ubu14;
Font source11, source12, source14;
Font GuiUbu11, GuiUbu12, GuiUbu14;
public void loadCurrentFont() {
  linuxFont12 = createFont(sketchPath() +"/data/UbuntuMono.ttf", 12);
  linuxFont11 = createFont(sketchPath() +"/data/UbuntuMono.ttf", 11);
  linuxFont14 = createFont(sketchPath() +"/data/UbuntuMono.ttf", 14);
  s12 = createFont(sketchPath() +"/data/SourceCodePro.ttf", 12);
  s11 = createFont(sketchPath() +"/data/SourceCodePro.ttf", 11);
  s14 = createFont(sketchPath() +"/data/SourceCodePro.ttf", 14);
  ubu12 = createFont(sketchPath() +"/data/Ubuntu.ttf", 12);
  ubu11 = createFont(sketchPath() +"/data/Ubuntu.ttf", 11);
  ubu14 = createFont(sketchPath() +"/data/Ubuntu.ttf", 14);

  source11 = new Font("SourceCodePro", Font.PLAIN, 11);
  source12 = new Font("SourceCodePro", Font.PLAIN, 12);
  source14 = new Font("SourceCodePro", Font.PLAIN, 14);

  try {
    GuiUbu11 = Font.createFont(Font.TRUETYPE_FONT, new File(sketchPath() +"/data/Ubuntu.ttf")).deriveFont(11f);
    GuiUbu12 = Font.createFont(Font.TRUETYPE_FONT, new File(sketchPath() +"/data/Ubuntu.ttf")).deriveFont(12f);
    GuiUbu14 = Font.createFont(Font.TRUETYPE_FONT, new File(sketchPath() +"/data/Ubuntu.ttf")).deriveFont(14f);
  }
  catch(Exception e)
  {
    e.printStackTrace();
  }
}
GWindow voiceSettingWindow;
GTextField inputPitch, inputRate, inputVolume;
GButton buttonSaveConfig;
//function
public void voiceSetting() {
  voiceSettingWindow = GWindow.getWindow(this, "Voice Tuning", 50, 100, 500, 140, JAVA2D); 
  voiceSettingWindow.noLoop();
  voiceSettingWindow.setActionOnClose(G4P.CLOSE_WINDOW);
  voiceSettingWindow.addDrawHandler(this, "win_drawVoiceSetting");

  inputPitch = new GTextField(voiceSettingWindow, 100, 30, 60, 20, G4P.SCROLLBARS_NONE);
  inputPitch.setText(voicePitch);
  inputPitch.setOpaque(false);
  inputPitch.setFont(GuiUbu11);

  inputRate = new GTextField(voiceSettingWindow, inputPitch.getX()+inputPitch.getWidth()+100, 30, 60, 20, G4P.SCROLLBARS_NONE);
  inputRate.setText(voiceRate);
  inputRate.setOpaque(false);
  inputRate.setFont(GuiUbu11);

  inputVolume = new GTextField(voiceSettingWindow, inputRate.getX()+inputRate.getWidth()+100, 30, 60, 20, G4P.SCROLLBARS_NONE);
  inputVolume.setText(voiceVolume);
  inputVolume.setOpaque(false);
  inputVolume.setFont(GuiUbu11);

  buttonSaveConfig = new GButton(voiceSettingWindow, voiceSettingWindow.width/2-75, inputVolume.getY()+40, 150, 30);
  buttonSaveConfig.setText("save configuration");
  buttonSaveConfig.setOpaque(false);
  buttonSaveConfig.addEventHandler(this, "buttonSaveVoiceConfiguration");
  buttonSaveConfig.setFont(GuiUbu11);

  voiceSettingWindow.loop();
}

public void loadVoiceSetting() {
  String currentPath = sketchPath() +"/data/voice.cfg";
  String[] file = loadStrings(currentPath);
  String text = "";
  for (int i=0; i<file.length; i++)
    text += file[i];
  String[] list = split(text, ",");
  voicePitch = list[0];
  voiceRate = list[1];
  voiceVolume = list[2];
  println("pitch " +voicePitch +" rate " +voiceRate +" volume " +voiceVolume);
}

PrintWriter output;
public void saveTextFunction() {
  output = createWriter(newSettingFilePath); 
  String data = "list koridor : ";
  output.println("total koridor : " +korNum);
  for (int i=0; i<korNum; i++) {
    if (i>0)
      data += ",";
    data += myKoridor[i].namaKoridor;
  }
  output.println(data);
  for (int i=0; i<korNum; i++) {
    data = "list halte : ";
    output.println("total halte (jalur pergi) koridor "+i +" : " +myKoridor[i].totalHalteGo);
    for (int j=0; j<myKoridor[i].totalHalteGo; j++) {
      if (j>0)
        data += ",";
      data += myKoridor[i].namaHalteGo.get(j);
    }
    output.println(data);
    data = "list halte : ";
    output.println("total halte (jalur pulang) koridor "+i +" : " +myKoridor[i].totalHalteBack);
    for (int j=0; j<myKoridor[i].totalHalteBack; j++) {
      if (j>0)
        data += ",";
      data += myKoridor[i].namaHalteBack.get(j);
    }
    output.println(data);
    data = "list text indoor : ";
  }
  for (int i=0; i<textIndoor.size(); i++) {
    if (i>0)
      data += ",";
    data += textIndoor.get(i);
  }
  output.println(data);
  data = "list text outoor : s";
  for (int i=0; i<textOutdoor.size(); i++) {
    if (i>0)
      data += ",";
    data += textOutdoor.get(i);
  }
  output.println(data);
  data = "";

  output.flush(); // Writes the remaining data to the file
  output.close(); // Finishes the file
  //exit(); // Stops the program
  //return 1;
}

GWindow newSettingWindow;
GLabel labelText, labela, labelb;
GButton buttonNext, buttonContinue, buttonBack, editHalte, buttonBrowse;
GTextField inputA, in1, in2; 
GTextField[] inB;
GOption optYes, optNo;
GToggleGroup optGroup;
GTabManager tab;
public void newSettingWizard() {
  newSettingWindow = GWindow.getWindow(this, "New Setting Wizard", 20, 20, 500, 300, JAVA2D); 
  newSettingWindow.noLoop();
  newSettingWindow.setActionOnClose(G4P.CLOSE_WINDOW);
  newSettingWindow.addDrawHandler(this, "win_drawNewSetting");
  //media
  buttonContinue = new GButton(newSettingWindow, newSettingWindow.width-70, newSettingWindow.height-30, 60, 25);
  buttonContinue.setOpaque(false);
  buttonContinue.setText("continue");
  buttonContinue.addEventHandler(this, "buttonContinueHandler");
  buttonContinue.setFont(GuiUbu11);
  inputA = new GTextField(newSettingWindow, 10, 65, 260, 20, G4P.SCROLLBARS_NONE);
  inputA.setOpaque(false);
  inputA.addEventHandler(this, "inputAHandler");
  inputA.setPromptText("input total koridor");
  inputA.setFont(GuiUbu11);
  inB = new GTextField[5];
  inB[0] = new GTextField(newSettingWindow, 10, 65, 260, 20, G4P.SCROLLBARS_NONE);
  inB[0].setOpaque(false);
  inB[0].addEventHandler(this, "inB1Handler");
  inB[0].setFont(GuiUbu11);
  inB[1] = new GTextField(newSettingWindow, 10, inB[0].getY()+30, 260, 20, G4P.SCROLLBARS_NONE);
  inB[1].setOpaque(false);
  inB[1].addEventHandler(this, "inB2Handler");
  inB[1].setFont(GuiUbu11);
  inB[2] = new GTextField(newSettingWindow, 10, inB[1].getY()+30, 260, 20, G4P.SCROLLBARS_NONE);
  inB[2].setOpaque(false);
  inB[2].addEventHandler(this, "inB3Handler");
  inB[2].setFont(GuiUbu11);
  inB[3] = new GTextField(newSettingWindow, 10, inB[2].getY()+30, 260, 20, G4P.SCROLLBARS_NONE);
  inB[3].setOpaque(false);
  inB[3].addEventHandler(this, "inB4Handler");
  inB[3].setFont(GuiUbu11);
  inB[4] = new GTextField(newSettingWindow, 10, inB[3].getY()+30, 260, 20, G4P.SCROLLBARS_NONE);
  inB[4].setOpaque(false);
  inB[4].addEventHandler(this, "inB5Handler");
  inB[4].setFont(GuiUbu11);

  buttonNext = new GButton(newSettingWindow, newSettingWindow.width-70, newSettingWindow.height-30, 50, 25);
  buttonNext.setOpaque(false);
  buttonNext.setText("next");
  buttonNext.addEventHandler(this, "buttonNextHandler");
  buttonNext.setFont(GuiUbu11);
  buttonBack = new GButton(newSettingWindow, buttonNext.getX()-buttonNext.getWidth()- 20, newSettingWindow.height-30, 50, 25);
  buttonBack.setOpaque(false);
  buttonBack.setText("back");
  buttonBack.addEventHandler(this, "buttonBackHandler");
  buttonBack.setFont(GuiUbu11);
  optYes = new GOption(newSettingWindow, 310, 90, 60, 30);
  optYes.addEventHandler(this, "optYesHandler");
  optYes.setText("ya");
  optYes.setOpaque(false);
  optYes.setFont(GuiUbu11);
  optYes.setSelected(true);
  optNo = new GOption(newSettingWindow, optYes.getX()+60, 90, 60, 30);
  optNo.addEventHandler(this, "optNoHandler");
  optNo.setText("tidak");
  optNo.setOpaque(false);
  optNo.setFont(GuiUbu11);
  optGroup = new GToggleGroup();
  optGroup.addControl(optYes);
  optGroup.addControl(optNo);
  in1 = new GTextField(newSettingWindow, 10, 140, 260, 20, G4P.SCROLLBARS_NONE);
  in1.setVisible(false);
  in1.setOpaque(false);
  in1.addEventHandler(this, "in1Handler");
  in1.setPromptText("total halte (jalur pergi)");
  in1.setFont(GuiUbu11);
  in2 = new GTextField(newSettingWindow, 10, in1.getY()+50, 260, 20, G4P.SCROLLBARS_NONE);
  in2.setVisible(false);
  in2.setOpaque(false);
  in2.addEventHandler(this, "in2Handler");
  in2.setPromptText("total halte (jalur pulang)");
  in2.setFont(GuiUbu11);
  tab = new GTabManager();
  tab.addControls(inputA, in1, in2);
  editHalte = new GButton(newSettingWindow, optYes.getX(), in1.getY(), 80, 20);
  editHalte.setText("edit halte");
  editHalte.setOpaque(false);
  editHalte.setVisible(false);
  editHalte.addEventHandler(this, "editHalteHandler");
  editHalte.setFont(GuiUbu11);
  buttonBrowse = new GButton(newSettingWindow, 10, newSettingWindow.height - 30, 80, 20);
  buttonBrowse.setText("browse");
  buttonBrowse.setOpaque(false);
  buttonBrowse.setVisible(false);
  buttonBrowse.addEventHandler(this, "browseHandler");
  buttonBrowse.setFont(GuiUbu11);
  //value init
  korNum = 0; 
  slideNum = 0; 
  newSetSlideNum = 0;
  GUINewSetSlideNum0();
  newSettingWindow.loop();
}

GWindow newHalteWindow;
GPanel panelHalte;
GTextField[] inputHalteGo;
GTextField[] inputHalteBack;
public void createListHalte() {
  myKoridor[slideNum-1].totalHalteGo = PApplet.parseInt(in1.getText());
  if (!in2.isVisible()) {
    myKoridor[slideNum-1].totalHalteBack = PApplet.parseInt(in1.getText());
  } else {
    myKoridor[slideNum-1].totalHalteBack = PApplet.parseInt(in2.getText());
  }
  println(myKoridor[slideNum-1].totalHalteBack);
  println(myKoridor[slideNum-1].totalHalteBack);

  newHalteWindow = GWindow.getWindow(this, "edit halte", 120, 20, 500, 300, JAVA2D);
  newHalteWindow.noLoop();
  newHalteWindow.setActionOnClose(G4P.CLOSE_WINDOW);
  newHalteWindow.addDrawHandler(this, "newHalteWindowHandler");
  newHalteWindow.addKeyHandler(this, "newHalteWindowKeyHandler");
  newHalteWindow.addMouseHandler(this, "newHalteWindowMouseHandler");

  panelHalte = new GPanel(newHalteWindow, 0, 0, newHalteWindow.width - 20, newHalteWindow.height, "here");
  panelHalte.setOpaque(false);

  inputHalteGo = new GTextField[myKoridor[slideNum-1].totalHalteGo];
  inputHalteBack = new GTextField[myKoridor[slideNum-1].totalHalteBack];  
  GLabel[] labelHalteGo = new GLabel[myKoridor[slideNum-1].totalHalteGo];
  GLabel[] labelHalteBack = new GLabel[myKoridor[slideNum-1].totalHalteBack];

  GTabManager tabHalte = new GTabManager();

  for (int i=0; i<myKoridor[slideNum-1].totalHalteGo; i++) {
    inputHalteGo[i] = new GTextField(newHalteWindow, 10, 20 + (i*40), 360, 20);
    inputHalteGo[i].setOpaque(false);
    inputHalteGo[i].setPromptText("input nama halte " +(i+1) +" (jalur pergi)");
    inputHalteGo[i].setFont(GuiUbu11);

    labelHalteGo[i] = new GLabel(newHalteWindow, 10, inputHalteGo[i].getY() - 20, 200, 20);
    labelHalteGo[i].setOpaque(false);
    labelHalteGo[i].setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
    labelHalteGo[i].setFont(GuiUbu11);
    labelHalteGo[i].setText("input nama halte " +(i+1) +" (jalur pergi)");

    panelHalte.addControl(inputHalteGo[i]);
    panelHalte.addControl(labelHalteGo[i]);
    tabHalte.addControl(inputHalteGo[i]);
  }
  for (int i=0; i<myKoridor[slideNum-1].totalHalteBack; i++) {
    inputHalteBack[i] = new GTextField(newHalteWindow, 10, (60 + inputHalteGo[inputHalteGo.length-1].getY()) + (i*40), 360, 20);
    inputHalteBack[i].setOpaque(false);
    inputHalteBack[i].setPromptText("input nama halte " +(i+1) +" (jalur pulang)");
    inputHalteBack[i].setFont(GuiUbu11);

    labelHalteBack[i] = new GLabel(newHalteWindow, 10, inputHalteBack[i].getY() - 20, 200, 20);
    labelHalteBack[i].setOpaque(false);
    labelHalteBack[i].setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
    labelHalteBack[i].setFont(GuiUbu11);
    labelHalteBack[i].setText("input nama halte " +(i+1) +" (jalur pulang)");

    panelHalte.addControl(inputHalteBack[i]);
    panelHalte.addControl(labelHalteBack[i]);
    tabHalte.addControl(inputHalteBack[i]);
  }

  GLabel labelKoridorTitle = new GLabel(newHalteWindow, newHalteWindow.width - 120, 10, 110, newHalteWindow.height - 60);
  labelKoridorTitle.setOpaque(false);
  labelKoridorTitle.setLocalColorScheme(PApplet.parseInt(random(0, 7)));
  labelKoridorTitle.setTextAlign(GAlign.CENTER, GAlign.TOP);
  labelKoridorTitle.setFont(GuiUbu14);
  labelKoridorTitle.setText("koridor " +myKoridor[slideNum-1].namaKoridor);

  GButton saveHalte = new GButton(newHalteWindow, newHalteWindow.width - 90, newHalteWindow.height - 40, 60, 25);
  saveHalte.setText("save");
  saveHalte.setOpaque(false);
  saveHalte.addEventHandler(this, "saveHalteHandler");
  saveHalte.setFont(GuiUbu11);

  //load pre-data to input
  if (myKoridor[slideNum-1].namaHalteGo.size() <= myKoridor[slideNum-1].totalHalteGo) {
    for (int i=0; i<myKoridor[slideNum-1].namaHalteGo.size(); i++)
      inputHalteGo[i].setText(myKoridor[slideNum-1].namaHalteGo.get(i));
  } else if (myKoridor[slideNum-1].namaHalteGo.size() > myKoridor[slideNum-1].totalHalteGo) {
    for (int i=0; i<myKoridor[slideNum-1].totalHalteGo; i++)
      inputHalteGo[i].setText(myKoridor[slideNum-1].namaHalteGo.get(i));
  }
  if (myKoridor[slideNum-1].namaHalteBack.size() <= myKoridor[slideNum-1].totalHalteBack) {
    for (int i=0; i<myKoridor[slideNum-1].namaHalteBack.size(); i++)
      inputHalteBack[i].setText(myKoridor[slideNum-1].namaHalteBack.get(i));
  } else if (myKoridor[slideNum-1].namaHalteBack.size() > myKoridor[slideNum-1].totalHalteBack) {
    for (int i=0; i<myKoridor[slideNum-1].totalHalteBack; i++)
      inputHalteBack[i].setText(myKoridor[slideNum-1].namaHalteBack.get(i));
  }

  //add handler to the last input
  inputHalteBack[myKoridor[slideNum-1].totalHalteBack-1].addEventHandler(this, "halteEnterHandler");
  newHalteWindow.loop();
}
GWindow newWriteToSDWindow;
GButton buttonLoadSetting, buttonWriteText, buttonWriteVoice;
public void createWriteToSDWindow() {
  newWriteToSDWindow = GWindow.getWindow(this, "write to sd", 150, 20, 500, 300, JAVA2D);
  newWriteToSDWindow.noLoop();
  newWriteToSDWindow.setActionOnClose(G4P.CLOSE_WINDOW);
  newWriteToSDWindow.addDrawHandler(this, "newWriteToSDWindowHandler");
  //newWriteToSDWindow.addKeyHandler(this, "newHalteWindowKeyHandler");
  //newWriteToSDWindow.addMouseHandler(this, "newHalteWindowMouseHandler");

  buttonLoadSetting = new GButton(newWriteToSDWindow, 10, 40, 90, 30);
  buttonLoadSetting.setText("load setting");
  buttonLoadSetting.setOpaque(false);
  buttonLoadSetting.addEventHandler(this, "buttonLoadSettingHandler");
  buttonLoadSetting.setFont(GuiUbu11);

  buttonWriteText = new GButton(newWriteToSDWindow, 10, buttonLoadSetting.getY() + 40, 90, 30);
  buttonWriteText.setText("write text");
  buttonWriteText.setOpaque(false);
  buttonWriteText.addEventHandler(this, "buttonWriteTextHandler");
  buttonWriteText.setFont(GuiUbu11);

  buttonWriteVoice = new GButton(newWriteToSDWindow, 10, buttonWriteText.getY() + 40, 90, 30);
  buttonWriteVoice.setText("write voice");
  buttonWriteVoice.setOpaque(false);
  buttonWriteVoice.addEventHandler(this, "buttonWriteVoiceHandler");
  buttonWriteVoice.setFont(GuiUbu11);

  newWriteToSDWindow.loop();
}
//load pre data new setting wizard
public void loadPreDataNewWizard() {
  inputA.setText(myKoridor[slideNum-1].namaKoridor);
  if (myKoridor[slideNum-1].choice)
    yesOptionFunction();
  else
    noOptionFunction();
}
public void loadPreDataNewWizard2() {
  if (textIndoor.size() > 0) {
    for (int i=0; i<textIndoor.size(); i++)
      inB[i].setText(textIndoor.get(i));
  }
  for (int i=textIndoor.size(); i<5; i++)
    inB[i].setText("");
}
public void loadPreDataNewWizard3() {
  if (textOutdoor.size() > 0) {
    for (int i=0; i<textOutdoor.size(); i++)
      inB[i].setText(textOutdoor.get(i));
  }
  for (int i=textOutdoor.size(); i<5; i++)
    inB[i].setText("");
}
//halte edit function
public void saveCurrentHalte() {
  if (myKoridor[slideNum-1].namaHalteGo.size() == myKoridor[slideNum-1].totalHalteGo) {
    //set data 
    for (int i=0; i<myKoridor[slideNum-1].totalHalteGo; i++)
      myKoridor[slideNum-1].namaHalteGo.set(i, inputHalteGo[i].getText());
  } else if (myKoridor[slideNum-1].namaHalteGo.size() < myKoridor[slideNum-1].totalHalteGo) {
    //remove data then add new data
    myKoridor[slideNum-1].namaHalteGo.clear();
    for (int i=0; i<myKoridor[slideNum-1].totalHalteGo; i++)
      myKoridor[slideNum-1].namaHalteGo.append(inputHalteGo[i].getText());
  } else if (myKoridor[slideNum-1].namaHalteGo.size() > myKoridor[slideNum-1].totalHalteGo) {
    //remove data then add new data
    myKoridor[slideNum-1].namaHalteGo.clear();
    for (int i=0; i<myKoridor[slideNum-1].totalHalteGo; i++)
      myKoridor[slideNum-1].namaHalteGo.append(inputHalteGo[i].getText());
  }

  printArray(myKoridor[slideNum-1].namaHalteGo);
  if (myKoridor[slideNum-1].namaHalteBack.size() == myKoridor[slideNum-1].totalHalteBack) {
    //set data 
    for (int i=0; i<myKoridor[slideNum-1].totalHalteBack; i++)
      myKoridor[slideNum-1].namaHalteBack.set(i, inputHalteBack[i].getText());
  } else if (myKoridor[slideNum-1].namaHalteBack.size() < myKoridor[slideNum-1].totalHalteBack) {
    //remove data then add new data
    myKoridor[slideNum-1].namaHalteBack.clear();
    for (int i=0; i<myKoridor[slideNum-1].totalHalteBack; i++)
      myKoridor[slideNum-1].namaHalteBack.append(inputHalteBack[i].getText());
  } else if (myKoridor[slideNum-1].namaHalteBack.size() > myKoridor[slideNum-1].totalHalteBack) {
    //remove data then add new data
    myKoridor[slideNum-1].namaHalteBack.clear();
    for (int i=0; i<myKoridor[slideNum-1].totalHalteBack; i++)
      myKoridor[slideNum-1].namaHalteBack.append(inputHalteBack[i].getText());
  }
  printArray(myKoridor[slideNum-1].namaHalteBack);
  newHalteWindow.close();
  newHalteWindow = null;
}

//button function
public void continueButtonFuntion() {
  korNum = PApplet.parseInt(inputA.getText());
  if (korNum != 0 || !inputA.getText().isEmpty()) {
    myKoridor = new koridor[korNum];
    println(myKoridor.length);
    newSetSlideNum = 1;
    slideNum += 1;
    myKoridor[slideNum-1] = new koridor();
    inputA.setPromptText("Nama Koridor " +slideNum);
    loadPreDataNewWizard();
    GUINewSetSlideNum1();
  }
}
public void nextButtonFuntion() {
  if (newSetSlideNum == 1) {
    if (slideNum >= korNum) {
      myKoridor[slideNum-1].namaKoridor = inputA.getText();
      newSetSlideNum = 2;
      GUINewSetSlideNum2();
      loadPreDataNewWizard2();
    } else {
      if (newSetSlideNum == 1) {
        myKoridor[slideNum] = new koridor();
        myKoridor[slideNum-1].namaKoridor = inputA.getText();
        println(myKoridor[slideNum-1].namaKoridor);
        if (in2.isVisible()) {
          myKoridor[slideNum-1].totalHalteGo = PApplet.parseInt(in1.getText());
          myKoridor[slideNum-1].totalHalteBack = PApplet.parseInt(in2.getText());
        } else {
          myKoridor[slideNum-1].totalHalteGo = PApplet.parseInt(in1.getText());
          myKoridor[slideNum-1].totalHalteBack = PApplet.parseInt(in1.getText());
        }
      }
      slideNum += 1;
      loadPreDataNewWizard();
    }
    in1.setText(""); 
    in2.setText("");
    inputA.setPromptText("Nama Koridor " +slideNum);
  } else if (newSetSlideNum == 2) {
    if (!inB[0].getText().isEmpty()) {
      //add new data
      if (textIndoor.size() > 0)
        textIndoor.set(0, inB[0].getText());
      else
        textIndoor.append(inB[0].getText());
      for (int i=1; i<5; i++) {
        if (!inB[i].getText().isEmpty()) {
          if (textIndoor.size() > i)
            textIndoor.set(i, inB[i].getText());
          else
            textIndoor.append(inB[i].getText());
        }
      }

      newSetSlideNum = 3;
      buttonNext.setText("finish");
      GUINewSetSlideNum3();
      loadPreDataNewWizard3();
    }
  } else if (newSetSlideNum == 3) {
    if (!newSettingFilePath.isEmpty() && newSettingFilePath != null) {
      //save function
      if (!inB[0].getText().isEmpty()) {
        //add new data
        if (textOutdoor.size() > 0)
          textOutdoor.set(0, inB[0].getText());
        else
          textOutdoor.append(inB[0].getText());

        for (int i=1; i<5; i++) {
          if (!inB[i].getText().isEmpty()) {
            if (textOutdoor.size() > i)
              textOutdoor.set(i, inB[i].getText());
            else
              textOutdoor.append(inB[i].getText());
          }
        }
      }
      saveTextFunction();
      newSettingWindow.close(); 
      newSettingWindow = null;
      println("saved to " +newSettingFilePath);
    } else {
      println("no path file");
    }
  }
}
public void backButtonFunction() {
  if (newSetSlideNum == 1) {
    inputA.setText("");
    slideNum -= 1;
    if (slideNum < 1) {
      newSetSlideNum = 0;
      slideNum = 0;
      GUINewSetSlideNum0();
      inputA.setPromptText("Input Jumlah Koridor");
    } else {
      inputA.setPromptText("Nama Koridor " +slideNum);
      loadPreDataNewWizard();
    }
  } else if (newSetSlideNum == 2) {
    newSetSlideNum = 1; 
    GUINewSetSlideNum1();
    loadPreDataNewWizard();
  } else if (newSetSlideNum == 3) {
    if (!inB[0].getText().isEmpty()) {
      //add new data
      if (textOutdoor.size() > 0)
        textOutdoor.set(0, inB[0].getText());
      else
        textOutdoor.append(inB[0].getText());

      for (int i=1; i<5; i++) {
        if (!inB[i].getText().isEmpty()) {
          if (textOutdoor.size() > i)
            textOutdoor.set(i, inB[i].getText());
          else
            textOutdoor.append(inB[i].getText());
        }
      }
    }

    newSetSlideNum = 2; 
    buttonNext.setText("next");
    loadPreDataNewWizard2();
  }
}
// option function
public void yesOptionFunction() {
  in1.setVisible(true);
  if (in2 != null)
    in2.setVisible(false);
  editHalte.setVisible(true);
}
public void noOptionFunction() {
  in1.setVisible(true);
  in2.setVisible(true);
  editHalte.setVisible(true);
}
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
   newSettingWizard();
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
//handler button
public void buttonSaveVoiceConfiguration(GButton source, GEvent event) {
  println("voice config saved");
  String[] text = {inputPitch.getText() +"," +inputRate.getText() +"," +inputVolume.getText()};
  String currentPath = sketchPath() + "/data/voice.cfg";
  println(currentPath);
  saveStrings(currentPath, text);
  loadVoiceSetting();
  voiceSettingWindow.close();
  voiceSettingWindow = null;
}
public void buttonExit() {
  exit();
}
public void buttonContinueHandler(GButton source, GEvent event) {
  continueButtonFuntion();
}
public void buttonNextHandler(GButton source, GEvent event) {
  nextButtonFuntion();
}
public void buttonBackHandler(GButton source, GEvent event) {
  backButtonFunction();
}
public void editHalteHandler(GButton source, GEvent event) {
  myKoridor[slideNum-1].namaKoridor = inputA.getText();
  if (!myKoridor[slideNum-1].namaKoridor.isEmpty())
    createListHalte();
}
public void inB1Handler(GButton source, GEvent event) {
}
public void inB2Handler(GButton source, GEvent event) {
}
public void inB3Handler(GButton source, GEvent event) {
}
public void inB4Handler(GButton source, GEvent event) {
}
public void inB5Handler(GButton source, GEvent event) {
}
public void saveHalteHandler(GButton source, GEvent event) {
  saveCurrentHalte();
}
public void buttonLoadSettingHandler(GButton source, GEvent event) {
  selectInput("Select a file to process:", "loadSettingLoaded");
}
public void loadSettingLoaded(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    loadSettingPath = selection.getAbsolutePath();
  }
}
public void buttonWriteTextHandler(GButton source, GEvent event) {
  if (!loadSettingPath.isEmpty()) {
    String lines[] = loadStrings(loadSettingPath);
    println(lines.length);
    for (int i = 0; i < lines.length; i++) {
      println(lines[i]);
      //String[] list = split(lines[i], ',');
      //for (int j=0; j<list.length; j++) {
      //  String[] koridorFiles = loadStrings(path +"\\" +list[j]);
      //  loadFilesToArea(koridorFiles, j, i);
      //}
    }
  }
  newWriteToSDWindow.close();
  newWriteToSDWindow = null;
}
public void buttonWriteVoiceHandler(GButton source, GEvent event) {

}
boolean browsed = false;
public void browseHandler(GButton source, GEvent event) {
  selectOutput("Select a file to write to:", "fileSelected");
}
public void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    browsed = true;
    newSettingFilePath = selection.getAbsolutePath();
  }
}
//handler option
public void optYesHandler(GOption source, GEvent event) {
  myKoridor[slideNum-1].choice = true;
  optYes.setSelected(true);
  optNo.setSelected(false);
  yesOptionFunction();
}
public void optNoHandler(GOption source, GEvent event) {
  myKoridor[slideNum-1].choice = false;
  optYes.setSelected(false);
  optNo.setSelected(true);
  noOptionFunction() ;
}
//handler input
public void inputAHandler(GTextField source, GEvent event) {
  if (event == GEvent.ENTERED && newSetSlideNum == 0) {
    continueButtonFuntion();
  }
}
public void in1Handler(GTextField source, GEvent event) {
  if (event == GEvent.ENTERED && !in2.isVisible()) {
    myKoridor[slideNum-1].namaKoridor = inputA.getText();
    if (!myKoridor[slideNum-1].namaKoridor.isEmpty())
      createListHalte();
  }
}
public  void in2Handler(GTextField source, GEvent event) {
  if (event == GEvent.ENTERED) {
    myKoridor[slideNum-1].namaKoridor = inputA.getText();
    if (!myKoridor[slideNum-1].namaKoridor.isEmpty())
      createListHalte();
  }
}
public void halteEnterHandler(GTextField source, GEvent event) {
  if (event == GEvent.ENTERED) {
    saveCurrentHalte();
  }
}
//handler key
public void newHalteWindowKeyHandler(PApplet app, GWinData data, KeyEvent event) {
  if (event.getAction() == KeyEvent.PRESS && app.keyCode == UP) {
    panelHalte.moveTo(0, panelHalte.getY()-2);
  } else if (event.getAction() == KeyEvent.PRESS && app.keyCode == DOWN) {
    panelHalte.moveTo(0, panelHalte.getY()+2);
  }
}
//handler mouse
public void newHalteWindowMouseHandler(PApplet app, GWinData data, MouseEvent mevent) {
  if (mevent.getAction() == MouseEvent.WHEEL) {
    panelHalte.moveTo(0, panelHalte.getY() +(10*mevent.getCount()));
  }
}
public class koridor {
  // The variables can be anything you like.
  String namaKoridor;
  int totalHalteGo, totalHalteBack;
  StringList namaHalteGo, namaHalteBack;
  boolean choice;
  koridor(){
    namaKoridor = "";
    totalHalteBack = 0;
    totalHalteGo = 0;
    namaHalteGo = new StringList();
    namaHalteBack = new StringList();
    choice = true;
  }
}
  public void settings() {  size(350, 190, JAVA2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ttsDesktop" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
