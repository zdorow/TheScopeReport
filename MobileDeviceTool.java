/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobiledevicetool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javafx.scene.image.Image;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import org.json.*;
import org.json.JSONObject;





/**
 *
 * @author ZD
 */
public class MobileDeviceTool extends Application {
        public static void main(String[] args)
    {
        launch(args);
    }

//Radio button naming   
RadioButton ComputerGroupsButton = new RadioButton("Computer");
RadioButton MobileGroupsButton = new RadioButton("Mobile");
RadioButton ProfilesCategoryButton = new RadioButton("Profiles");
RadioButton AppsCategoryButton = new RadioButton("Applications");
RadioButton PoliciesCategoryButton = new RadioButton("Policies");
RadioButton EbooksCategoryButton = new RadioButton("Ebooks");

private JSSapiCalls api;


    @Override
    public void start(Stage primaryStage) throws JssApiException {
        
//Radio button configurations
       ToggleGroup DeviceGroup = new ToggleGroup();
       ToggleGroup CategoryGroup = new ToggleGroup();
       DeviceGroup.getToggles().addAll(ComputerGroupsButton, MobileGroupsButton);  
       CategoryGroup.getToggles().addAll(ProfilesCategoryButton, AppsCategoryButton, PoliciesCategoryButton, EbooksCategoryButton);
       
//Selections for testing
ComputerGroupsButton.setSelected(true);
MobileGroupsButton.setSelected(false);
ProfilesCategoryButton.setSelected(false);
AppsCategoryButton.setSelected(false);
PoliciesCategoryButton.setSelected(true);
EbooksCategoryButton.setSelected(false);
//Labels for text fields
        Font font = new Font("Dialog", 18);
        Font font1 = new Font("Dialog Bold", 14);
                
/*      Label NamesLabel = new Label("Device Group Name(s) Blank=All:");
        NamesLabel.setFont(font);
        NamesLabel.setLineSpacing(20);
        GridPane.setHalignment(NamesLabel, HPos.CENTER);
*/        
        Label JamfProLabel = new Label("Jamf Pro URL:");
        JamfProLabel.setFont(font);
        JamfProLabel.setLineSpacing(20);
        GridPane.setHalignment(JamfProLabel, HPos.CENTER);
        
        Label JamfProUsernameLabel = new Label("Jamf Pro Username:");
        JamfProUsernameLabel.setFont(font);
        JamfProUsernameLabel.setLineSpacing(20);
        GridPane.setHalignment(JamfProUsernameLabel, HPos.CENTER);
        
        Label JamfProPasswordLabel = new Label("Jamf Pro Password:");
        JamfProPasswordLabel.setFont(font);
        JamfProPasswordLabel.setLineSpacing(20);
        GridPane.setHalignment(JamfProPasswordLabel, HPos.CENTER);
        
        Label OutputMessage = new Label("Please include https://");
        OutputMessage.setFont(font1);
        OutputMessage.setTextFill(Color.rgb(50, 60, 125));
                
        //Text Fields for processing input       
        TextField JamfProURLInput = new TextField();
        JamfProURLInput.setPromptText("");
        JamfProURLInput.setFont(font);
        GridPane.setHalignment(JamfProURLInput, HPos.CENTER);
        
        TextField JamfProUsernameInput = new TextField();
        JamfProUsernameInput.setPromptText("Jamf Pro Username ");
        JamfProUsernameInput.setFont(font);
        GridPane.setHalignment(JamfProUsernameInput, HPos.CENTER);
        
        PasswordField JamfProPasswordInput = new PasswordField();
        JamfProPasswordInput.setPromptText("Jamf Pro Password ");
        JamfProPasswordInput.setFont(font);
        GridPane.setHalignment(JamfProPasswordInput, HPos.CENTER);        
        
/*        TextField DeviceNamesInput = new TextField();
        DeviceNamesInput.setPromptText("Add device names followed by a comma (optional)");
        DeviceNamesInput.setFont(font);
        GridPane.setHalignment(DeviceNamesInput, HPos.CENTER);
*/        
        Button btn = new Button();
        btn.setText("Export group associations to text file");
        btn.setAlignment(Pos.BOTTOM_CENTER);    
      
//Vbox for input group
        VBox Input = new VBox(JamfProLabel, JamfProURLInput, JamfProUsernameLabel,
        JamfProUsernameInput, JamfProPasswordLabel, JamfProPasswordInput);
        Input.setAlignment(Pos.TOP_CENTER);
        Input.setPadding(new Insets(10, 10, 10, 10));
        Input.setSpacing(5);
        
//Radial Buttons        
        HBox DeviceGroupOptions = new HBox(ComputerGroupsButton, MobileGroupsButton);
        DeviceGroupOptions.setAlignment(Pos.CENTER);
        DeviceGroupOptions.setPadding(new Insets(10, 10, 10, 10));
        DeviceGroupOptions.setSpacing(18);
        
        HBox CategoryOptions = new HBox(ProfilesCategoryButton, AppsCategoryButton, PoliciesCategoryButton, EbooksCategoryButton);
        CategoryOptions.setAlignment(Pos.CENTER);
        CategoryOptions.setPadding(new Insets(10, 10, 10, 45));
        CategoryOptions.setSpacing(10);
        
//Bottom Button        
        HBox Button = new HBox(btn);
        Button.setAlignment(Pos.CENTER);
        Button.setPadding(new Insets(10, 10, 10, 10));
        Button.setSpacing(10);
            
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(Input, DeviceGroupOptions, CategoryOptions, 
        Button, OutputMessage);
                
        Scene scene = new Scene(root, 500, 400);
        
        primaryStage.getIcons().add(new Image("file:JamfLogo.png"));
        primaryStage.setTitle("The Device Groups Tool");
        primaryStage.setScene(scene);
        primaryStage.show();



    btn.setOnAction((ActionEvent event) -> {
        try {            
            String url = JamfProURLInput.getText();
            String username = JamfProUsernameInput.getText();
            String password = JamfProPasswordInput.getText();
            //String devices = "mobiledevicegroups";
            
            api = new JSSapiCalls(url, username, password, JSSapiCalls.FORMAT.JSON, JSSapiCalls.FORMAT.JSON);
            List<String> lines = Arrays.asList(url, username);
            DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("MM.dd.yyyy");
            
            if (PoliciesCategoryButton.isSelected() && ComputerGroupsButton.isSelected()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "CompPolcies.txt"))) {
                    OutputMessage.setText("Writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + "CompPolcies.txt");
                    JSONObject jsonPolicy = new JSONObject(api.get("policies"));
                    JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("policies");
                    for(int i=0; i<jsonPolicyArray.length(); i++){
                        JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                        int id = dataObj.getInt("id");
                        String name = dataObj.getString("name");
                        writer.write(name);
                        writer.newLine();
                        
                        JSONObject jsonScope = new JSONObject(api.get("policies/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("policy");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        Object compscope = scope.get("computer_groups").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                        writer.newLine();
                        writer.write((String) compscope);
                        writer.newLine();
                        writer.write("--------------------------------------------");
                        writer.newLine();
                        writer.flush();
                    }
                }
            }
            else if (AppsCategoryButton.isSelected() && ComputerGroupsButton.isSelected()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "CompApps.txt"))) {
                    OutputMessage.setText("Writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + "CompApps.txt");
                    JSONObject jsonPolicy = new JSONObject(api.get("macapplications"));
                    JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("mac_applications");
                    for(int i=0; i<jsonPolicyArray.length(); i++){
                        JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                        int id = dataObj.getInt("id");
                        String name = dataObj.getString("name");
                        System.out.println(name);
                        writer.write("Application: " + name );
                        writer.newLine();
                        JSONObject jsonScope = new JSONObject(api.get("macapplications/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("mac_application");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        Object compscope = scope.get("computer_groups").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                        System.out.println(compscope);
                        writer.newLine();
                        writer.write("Group: " + (String) compscope);
                        writer.newLine();
                        writer.write("--------------------------------------------");
                        writer.newLine();
                        writer.flush();
                    }
                }
            }
            else if (ProfilesCategoryButton.isSelected() && ComputerGroupsButton.isSelected()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "CompProfiles.txt"))) {
                    OutputMessage.setText( "Writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " CompProfiles.txt");
                    JSONObject jsonPolicy = new JSONObject(api.get("osxconfigurationprofiles"));
                    JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("os_x_configuration_profiles");
                    for(int i=0; i<jsonPolicyArray.length(); i++){
                        JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                        int id = dataObj.getInt("id");
                        String name = dataObj.getString("name");
                        writer.write(name);
                        writer.newLine();
                        
                        JSONObject jsonScope = new JSONObject(api.get("osxconfigurationprofiles/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("os_x_configuration_profile");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        Object compscope = scope.get("computer_groups").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                        writer.newLine();
                        writer.write("Group: " + (String) compscope);
                        writer.newLine();
                        writer.write("--------------------------------------------");
                        writer.newLine();
                        writer.flush();
                    }
                }
            }
            else if (EbooksCategoryButton.isSelected() && ComputerGroupsButton.isSelected()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "CompEbook.txt"))) {
                    OutputMessage.setText( "Writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MobileProfiles.txt");
                    JSONObject jsonPolicy = new JSONObject(api.get("ebooks"));
                    JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("ebooks");
                    for(int i=0; i<jsonPolicyArray.length(); i++){
                        JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                        int id = dataObj.getInt("id");
                        String name = dataObj.getString("name");
                        writer.write(name);
                        writer.newLine();
                        
                        JSONObject jsonScope = new JSONObject(api.get("ebooks/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("ebook");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        Object compscope = scope.get("computer_groups").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                        writer.newLine();
                        writer.write("Group: " + (String) compscope);
                        writer.newLine();
                        writer.write("--------------------------------------------");
                        writer.newLine();
                        writer.flush();
                    }
                }
            }
            else if (ProfilesCategoryButton.isSelected() && MobileGroupsButton.isSelected()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MobileProfiles.txt"))) {
                    OutputMessage.setText( "Writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MobileProfiles.txt");
                    JSONObject jsonPolicy = new JSONObject(api.get("mobiledeviceapplications"));
                    JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("mobile_device_applications");
                    for(int i=0; i<jsonPolicyArray.length(); i++){
                        JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                        int id = dataObj.getInt("id");
                        String name = dataObj.getString("name");
                        writer.write(name);
                        writer.newLine();
                        
                        JSONObject jsonScope = new JSONObject(api.get("mobiledeviceapplications/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("mobile_device_application");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        Object compscope = scope.get("mobile_device_groups").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                        writer.newLine();
                        writer.write("Group: " + (String) compscope);
                        writer.newLine();
                        writer.write("--------------------------------------------");
                        writer.newLine();
                        writer.flush();
                    }
                }
            }
            else if (AppsCategoryButton.isSelected() && MobileGroupsButton.isSelected()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MobileApps.txt"))) {
                    OutputMessage.setText( "Writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MobileApps.txt");
                    JSONObject jsonPolicy = new JSONObject(api.get("mobiledeviceconfigurationprofiles"));
                    JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("configuration_profiles");
                    for(int i=0; i<jsonPolicyArray.length(); i++){
                        JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                        int id = dataObj.getInt("id");
                        String name = dataObj.getString("name");
                        writer.write(name);
                        writer.newLine();
                        
                        JSONObject jsonScope = new JSONObject(api.get("mobiledeviceconfigurationprofiles/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("configuration_profile");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        Object compscope = scope.get("mobile_device_groups").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                        writer.newLine();
                        writer.write("Group: " + (String) compscope);
                        writer.newLine();
                        writer.write("--------------------------------------------");
                        writer.newLine();
                        writer.flush();
                    }
                }
            }
            else if (PoliciesCategoryButton.isSelected() && MobileGroupsButton.isSelected()) {
                OutputMessage.setText("You must be thinking of mobile profiles");
            }
            else if (EbooksCategoryButton.isSelected() && MobileGroupsButton.isSelected()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(timeStampPattern.format(java.time.LocalDateTime.now()) + "MobileEbooks.txt"))) {
                    OutputMessage.setText( "Writing to file " + timeStampPattern.format(java.time.LocalDateTime.now()) + " MobileEbooks.txt");
                    JSONObject jsonPolicy = new JSONObject(api.get("ebooks"));
                    JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("ebooks");
                    for(int i=0; i<jsonPolicyArray.length(); i++){
                        JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                        int id = dataObj.getInt("id");
                        String name = dataObj.getString("name");
                        writer.write(name);
                        writer.newLine();
                        
                        JSONObject jsonScope = new JSONObject(api.get("ebooks/id/" + id + "/subset/scope"));
                        JSONObject scopeObject = jsonScope.getJSONObject("ebook");
                        JSONObject scope = scopeObject.getJSONObject("scope");
                        Object compscope = scope.get("mobile_device_groups").toString().replace("[{", "").replace("}]", "").replace("\"", "");
                        writer.newLine();
                        writer.write("Group: " + (String) compscope);
                        writer.newLine();
                        writer.write("--------------------------------------------");
                        writer.newLine();
                        writer.flush();
                    }
                }
            }
            else
                OutputMessage.setText("Please Select a Type of Device Group and a Category");
        } catch (JssApiException | JSONException | IOException ex) {
            OutputMessage.setText( "URL, username and password not accepted.");
            Logger.getLogger(MobileDeviceTool.class.getName()).log(Level.SEVERE, null, ex);           
        }
    });
}
}

