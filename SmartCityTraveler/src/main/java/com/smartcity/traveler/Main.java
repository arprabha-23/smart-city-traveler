package com.smartcity.traveler;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.*;

public class Main extends Application {
    private Stage stage;
    private final List<User> users = new ArrayList<>();
    private final List<TravelOption> options = new ArrayList<>();
    private User currentUser;
    private TravelOption selectedOption;
    private Booking currentBooking;
    private final String css = getClass().getResource("/style.css").toExternalForm();

    @Override public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Smart City Traveler");
        stage.setMinWidth(900); stage.setMinHeight(620);
        loadData(); showLogin(); stage.show();
    }

    private void loadData() {
        users.add(new User("Demo User", "demo", "demo123"));
        options.add(new TravelOption("City Bus", "Central Bus Stand", "City Mall", 30, "08:30 AM", "Bus"));
        options.add(new TravelOption("Metro Express", "Central Station", "Airport", 60, "09:15 AM", "Metro"));
        options.add(new TravelOption("Intercity Train", "Railway Station", "Coimbatore", 180, "10:00 AM", "Train"));
        options.add(new TravelOption("City Taxi", "Bus Stand", "Hotel Grand", 250, "Available Now", "Taxi"));
        options.add(new TravelOption("City Palace", "Tourist Point", "City Palace", 100, "10:30 AM", "Tourist Place"));
        options.add(new TravelOption("Hotel Grand", "City Centre", "Hotel Grand", 1800, "Check-in 12:00 PM", "Hotel"));
        options.add(new TravelOption("Spice Garden", "City Centre", "Spice Garden", 500, "Open 11:00 AM", "Restaurant"));
    }

    private Scene scene(Region root) {
        Scene s = new Scene(root, 1000, 680); s.getStylesheets().add(css); return s;
    }
    private BorderPane page(String title, String subtitle) {
        BorderPane p = new BorderPane(); p.getStyleClass().add("page"); p.setTop(header(title, subtitle)); return p;
    }
    private VBox header(String title, String subtitle) {
        Label a = new Label(title), b = new Label(subtitle);
        a.getStyleClass().add("page-title"); b.getStyleClass().add("subtitle");
        VBox v = new VBox(5, a, b); v.setPadding(new Insets(25,35,18,35)); v.getStyleClass().add("header"); return v;
    }
    private VBox card() { VBox v = new VBox(14); v.setPadding(new Insets(28)); v.setMaxWidth(620); v.getStyleClass().add("card"); return v; }
    private Label label(String t) { Label l = new Label(t); l.getStyleClass().add("field-label"); return l; }
    private TextField field(String prompt) { TextField f = new TextField(); f.setPromptText(prompt); f.setPrefHeight(42); return f; }
    private PasswordField pass(String prompt) { PasswordField f = new PasswordField(); f.setPromptText(prompt); f.setPrefHeight(42); return f; }
    private Button primary(String t) { Button b = new Button(t); b.getStyleClass().add("primary-button"); b.setPrefHeight(42); return b; }
    private Button secondary(String t) { Button b = new Button(t); b.getStyleClass().add("secondary-button"); b.setPrefHeight(40); return b; }
    private void center(BorderPane root, VBox box) { StackPane s = new StackPane(box); s.setPadding(new Insets(15)); root.setCenter(s); }
    private String money(double n) { return String.format(Locale.US, "₹%.0f", n); }
    private void info(String title, String text) { Alert a = new Alert(Alert.AlertType.INFORMATION, text); a.setTitle(title); a.setHeaderText(null); a.showAndWait(); }
    private void error(String text) { Alert a = new Alert(Alert.AlertType.ERROR, text); a.setTitle("Input Error"); a.setHeaderText(null); a.showAndWait(); }

    private HBox nav() {
        HBox n = new HBox(10); n.setAlignment(Pos.CENTER); n.setPadding(new Insets(12,25,12,25)); n.getStyleClass().add("nav-bar");
        Button home=secondary("Home"), search=secondary("Search"), profile=secondary("Profile"), logout=secondary("Logout");
        home.setOnAction(e->showHome()); search.setOnAction(e->showSearch()); profile.setOnAction(e->showProfile()); logout.setOnAction(e->logout());
        n.getChildren().addAll(home,search,profile,logout); return n;
    }

    private void showLogin() {
        BorderPane root=page("Smart City Traveler","Login to continue your smart travel journey"); VBox box=card(); box.setMaxWidth(440);
        Label h=new Label("Welcome Back"), hint=new Label("Demo account: demo / demo123"); h.getStyleClass().add("section-title"); hint.getStyleClass().add("hint");
        TextField u=field("Username"); PasswordField p=pass("Password"); Button login=primary("Login"), signup=secondary("Create New Account");
        login.setOnAction(e->{User found=findUser(u.getText().trim()); if(found!=null&&found.getPassword().equals(p.getText())){currentUser=found;showHome();}else error("Invalid username or password. Try demo / demo123.");});
        signup.setOnAction(e->showSignup());
        box.getChildren().addAll(h,hint,label("Username"),u,label("Password"),p,login,signup); center(root,box); stage.setScene(scene(root));
    }

    private void showSignup() {
        BorderPane root=page("Create Account","Register a new Smart City Traveler account"); VBox box=card(); box.setMaxWidth(500); Label h=new Label("Sign Up"); h.getStyleClass().add("section-title");
        TextField name=field("Full name"), user=field("Email or username"); PasswordField pw=pass("Password"), confirm=pass("Confirm password"); Button create=primary("Sign Up"), back=secondary("Back to Login");
        create.setOnAction(e->{String n=name.getText().trim(), u=user.getText().trim(); if(n.isEmpty()||u.isEmpty()||pw.getText().isEmpty()||confirm.getText().isEmpty()){error("Please fill in all fields.");return;} if(!pw.getText().equals(confirm.getText())){error("Passwords do not match.");return;} if(findUser(u)!=null){error("That username already exists.");return;} users.add(new User(n,u,pw.getText())); info("Registration Successful","Account created. You can now log in."); showLogin();});
        back.setOnAction(e->showLogin());
        box.getChildren().addAll(h,label("Name"),name,label("Email / Username"),user,label("Password"),pw,label("Confirm Password"),confirm,create,back); center(root,box); stage.setScene(scene(root));
    }
    private User findUser(String username){for(User u:users)if(u.getUsername().equalsIgnoreCase(username))return u;return null;}

    private void showHome() {
        BorderPane root=page("Smart City Traveler","Smart travel options in one simple application"); root.setBottom(nav()); VBox content=new VBox(20); content.setAlignment(Pos.CENTER); content.setPadding(new Insets(35));
        Label w=new Label("Hello, "+currentUser.getName()+"!"), t=new Label("Search transport, tourist places, hotels and restaurants."); w.getStyleClass().add("hero-title"); t.getStyleClass().add("hero-text");
        HBox cards=new HBox(15); cards.setAlignment(Pos.CENTER); for(String x:new String[]{"Bus","Train","Metro","Taxi"}){VBox m=new VBox(new Label(x));m.setAlignment(Pos.CENTER);m.setPrefSize(150,95);m.getStyleClass().add("mini-card");cards.getChildren().add(m);}
        Button search=primary("Search Travel Options"), profile=secondary("Update Profile"); search.setOnAction(e->showSearch()); profile.setOnAction(e->showProfile());
        content.getChildren().addAll(w,t,cards,search,profile); root.setCenter(content); stage.setScene(scene(root));
    }

    private void showProfile() {
        BorderPane root=page("My Profile","Update your account information"); root.setBottom(nav()); VBox box=card(); box.setMaxWidth(520);
        TextField name=field("Name"), user=field("Email / Username"); name.setText(currentUser.getName()); user.setText(currentUser.getUsername()); PasswordField pw=pass("New password"); pw.setText(currentUser.getPassword());
        Button update=primary("Update Profile"), back=secondary("Back");
        update.setOnAction(e->{String n=name.getText().trim(),u=user.getText().trim(),p=pw.getText(); if(n.isEmpty()||u.isEmpty()||p.isEmpty()){error("All fields are required.");return;} User other=findUser(u); if(other!=null&&other!=currentUser){error("That username is already used by another account.");return;} currentUser.update(n,u,p); info("Success","Profile and password updated successfully.");showHome();});
        back.setOnAction(e->showHome()); box.getChildren().addAll(new Label("Update Profile"),label("Name"),name,label("Email / Username"),user,label("Password"),pw,update,back); center(root,box); stage.setScene(scene(root));
    }

    private void showSearch(){
        BorderPane root=page("Search Options","Find a bus, train, metro, taxi or city attraction"); root.setBottom(nav()); VBox box=card(); box.setMaxWidth(700); TextField q=field("Search by name, place or transport type");
        ComboBox<String> type=new ComboBox<>(FXCollections.observableArrayList("All","Bus","Train","Metro","Taxi","Tourist Place","Hotel","Restaurant")); type.setValue("All"); type.setPrefHeight(42);
        Button search=primary("Search"), all=secondary("Show All Options"); search.setOnAction(e->showResults(q.getText().trim(),type.getValue())); all.setOnAction(e->showResults("","All"));
        box.getChildren().addAll(new Label("Search Travel Options"),label("Keyword"),q,label("Category"),type,search,all); center(root,box); stage.setScene(scene(root));
    }

    private void showResults(String query,String type){
        BorderPane root=page("Search Results","Select an option to continue to booking"); root.setBottom(nav()); VBox list=new VBox(12); list.setPadding(new Insets(25,40,25,40)); String q=query.toLowerCase(Locale.ROOT); int count=0;
        for(TravelOption o:options){boolean mq=q.isEmpty()||o.getName().toLowerCase(Locale.ROOT).contains(q)||o.getSource().toLowerCase(Locale.ROOT).contains(q)||o.getDestination().toLowerCase(Locale.ROOT).contains(q)||o.getType().toLowerCase(Locale.ROOT).contains(q); boolean mt=type.equals("All")||o.getType().equals(type); if(mq&&mt){list.getChildren().add(optionCard(o));count++;}}
        if(count==0){Label none=new Label("No matching options found. Try another search.");none.getStyleClass().add("hint");list.getChildren().add(none);} ScrollPane scroll=new ScrollPane(list);scroll.setFitToWidth(true);scroll.getStyleClass().add("clean-scroll");root.setCenter(scroll);stage.setScene(scene(root));
    }
    private HBox optionCard(TravelOption o){
        VBox d=new VBox(5); Label n=new Label(o.getName()+"  •  "+o.getType()), r=new Label(o.getSource()+"  →  "+o.getDestination()), i=new Label("Time: "+o.getTime()+"    |    Price: "+money(o.getPrice())); n.getStyleClass().add("result-title");d.getChildren().addAll(n,r,i);
        Button select=primary("Select");select.setOnAction(e->{selectedOption=o;showBooking();}); HBox row=new HBox(18,d,select);HBox.setHgrow(d,Priority.ALWAYS);row.setAlignment(Pos.CENTER_LEFT);row.setPadding(new Insets(16));row.getStyleClass().add("result-card");return row;
    }

    private void showBooking(){
        BorderPane root=page("Booking","Enter your trip details and confirm the booking"); root.setBottom(nav()); VBox box=card(); box.setMaxWidth(620);
        Label selected=new Label("Selected: "+selectedOption.getName()+" — "+money(selectedOption.getPrice())+" per passenger"); selected.getStyleClass().add("selected-label");
        TextField source=field("Source"), destination=field("Destination"); source.setText(selectedOption.getSource());destination.setText(selectedOption.getDestination()); DatePicker date=new DatePicker(LocalDate.now()); date.setPrefHeight(42);
        Spinner<Integer> passengers=new Spinner<>(1,10,1);passengers.setPrefHeight(42);passengers.setMaxWidth(Double.MAX_VALUE); Label total=new Label("Total: "+money(selectedOption.getPrice()));total.getStyleClass().add("total-label");
        passengers.valueProperty().addListener((obs,oldV,newV)->total.setText("Total: "+money(selectedOption.getPrice()*newV)));
        Button book=primary("Book Now"), back=secondary("Back to Results");
        book.setOnAction(e->{if(source.getText().trim().isEmpty()||destination.getText().trim().isEmpty()||date.getValue()==null){error("Please enter source, destination and date.");return;}if(date.getValue().isBefore(LocalDate.now())){error("Booking date cannot be in the past.");return;}int count=passengers.getValue();double amount=selectedOption.getPrice()*count;String ref="SCT-"+UUID.randomUUID().toString().substring(0,8).toUpperCase(Locale.ROOT);currentBooking=new Booking(ref,selectedOption,source.getText().trim(),destination.getText().trim(),date.getValue().toString(),count,amount);showPayment();});
        back.setOnAction(e->showResults("","All")); box.getChildren().addAll(new Label("Trip Details"),selected,label("Source"),source,label("Destination"),destination,label("Date"),date,label("Passengers"),passengers,total,book,back);center(root,box);stage.setScene(scene(root));
    }

    private void showPayment(){
        BorderPane root=page("Payment","Demo payment — no real money is charged");root.setBottom(nav());VBox box=card();box.setMaxWidth(520);Label amount=new Label("Booking Amount: "+money(currentBooking.getAmount()));amount.getStyleClass().add("amount-label");
        ComboBox<String> method=new ComboBox<>(FXCollections.observableArrayList("Cash","UPI","Card"));method.setValue("UPI");method.setPrefHeight(42);Button pay=primary("Make Payment"),back=secondary("Back to Booking");
        pay.setOnAction(e->{info("Payment Successful","Payment completed using "+method.getValue()+".");showConfirmation();});back.setOnAction(e->showBooking());Label note=new Label("Demo only: no real payment gateway is connected.");note.getStyleClass().add("hint");note.setWrapText(true);
        box.getChildren().addAll(new Label("Complete Payment"),amount,label("Payment Method"),method,note,pay,back);center(root,box);stage.setScene(scene(root));
    }

    private void showConfirmation(){
        BorderPane root=page("Booking Confirmation","Your Smart City Traveler booking is confirmed");root.setBottom(nav());VBox box=card();box.setMaxWidth(620);box.setAlignment(Pos.CENTER);
        Label ok=new Label("✓ Booking Confirmed"),ref=new Label("Reference Number: "+currentBooking.getReference());ok.getStyleClass().add("success-title");ref.getStyleClass().add("reference");
        Label details=new Label("Option: "+currentBooking.getOption().getName()+"\nRoute: "+currentBooking.getSource()+" → "+currentBooking.getDestination()+"\nDate: "+currentBooking.getDate()+"\nPassengers: "+currentBooking.getPassengers()+"\nAmount Paid: "+money(currentBooking.getAmount()));details.getStyleClass().add("confirmation-details");
        Button home=primary("Back to Home"),another=secondary("Make Another Booking");home.setOnAction(e->showHome());another.setOnAction(e->showSearch());box.getChildren().addAll(ok,ref,details,home,another);center(root,box);stage.setScene(scene(root));
    }
    private void logout(){currentUser=null;selectedOption=null;currentBooking=null;showLogin();}
    public static void main(String[] args){launch(args);}
}
