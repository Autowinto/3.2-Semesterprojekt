package worldofzuul.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ObservableFaceArray;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import worldofzuul.model.*;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    private final Parser parser = new Parser();
    Power power = new Power();
    Product windmill;
    Product watermill;
    Product solarpanel;
    Inventory inventory = new Inventory();
    private Item[] allItems;
    private Room currentRoom;
    private int tutorialTextField;

    @FXML
    private Pane pane;
    @FXML
    private Label hoverLabel;
    @FXML
    private ListView<Item> inventoryListView;
    @FXML
    private ImageView roomBackground;
    @FXML
    private Label consoleLabel;
    @FXML
    private ProgressBar powerProgressBar;
    @FXML
    private Circle minimapCircle;
    @FXML
    private TextArea tutorialText;

    private final Image dropOffImage = new Image("/Scener/Kul.png");

    @FXML
    private ImageView powerImageView;


    private Image pære0 = new Image("/Scener/pære0.png");
    private Image pære1 = new Image("/Scener/pære1.jpg");
    private Image pære2 = new Image("/Scener/pære2.jpg");
    private Image pære3 = new Image("/Scener/pære3.jpg");
    private Image pære4 = new Image("/Scener/pære4.jpg");
    private Image pære5 = new Image("/Scener/pære5.jpg");
    private Image pære6 = new Image("/Scener/pære6.jpg");
    private Image pære7 = new Image("/Scener/pære7.jpg");
    private Image pære8 = new Image("/Scener/pære8.jpg");

    @FXML
    Pane root;
    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("START GAME");
        hoverLabel.setMouseTransparent(true);
        createItems();
        createRooms();
        initializeInventory();
        printWelcome();
    }

    public void endGame (ActionEvent event){
        try {
            root = FXMLLoader.load(GameController.class.getResource("/End.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void createItems() {
        try {
            allItems = new Item[]{
                    //Items
                    new Material("Vindgenerator", EnergyType.WIND, 200, 200, "/Scener/Vindgenerator.png","- Tryk på firkanten for at fjerne den -\nBeskrivelse af vindgenerator"),
                    new Material("Vinger", EnergyType.WIND,200,200,"/Scener/Vinger.png","- Tryk på firkanten for at fjerne den -\nBeskrivelse af vinger"),
                    new Material("Tårn", EnergyType.WIND,200,200,"/Scener/Tårn.png","- Tryk på firkanten for at fjerne den -\nBeskrivelse af tårn"),
                    new Material("Turbine", EnergyType.WATER,200,200,"/Scener/Turbine.png","- Tryk på firkanten for at fjerne den -\nBeskrivelse af turbine"),
                    new Material("Vandrør", EnergyType.WATER,200,200,"/Scener/Vandrør.png","- Tryk på firkanten for at fjerne den -\nBeskrivelse af vandrør"),
                    new Material("Vandgenerator", EnergyType.WATER,200,200,"/Scener/Vandgenerator.png","- Tryk på firkanten for at fjerne den -\nBeskrivelse af vandgenerator"),
                    new Material("Solpanel", EnergyType.SOLAR,200,200,"/Scener/Solpanel.png","- Tryk på firkanten for at fjerne den -\nBeskrivelse af solpanel"),
                    new Material("Inverter", EnergyType.SOLAR,200,200,"/Scener/Inverter.png","- Tryk på firkanten for at fjerne den -\nBeskrivelse af inverter"),
                    new Material("Glas", EnergyType.SOLAR,200,200,"/Scener/Glas.png","- Tryk på firkanten for at fjerne den -\nBeskrivelse af glas"),

                    //Products
                    new Product("Vindmølle", EnergyType.WIND,100,100,"/Scener/Vindkraft.png"),
                    new Product("Vandmølle", EnergyType.WATER,100,100,"/Scener/Vandkraft.png"),
                    new Product("Solcelle", EnergyType.SOLAR, 400, 300,"/Scener/Solkraft.png")};
            windmill = (Product) allItems[9];
            watermill = (Product) allItems[10];
            solarpanel = (Product) allItems[11];

            for (Item item : allItems) {
                item.setOnMouseClicked(itemClickHandler);

                item.hoverProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        this.hoverLabel.setTranslateX(item.getX());
                        this.hoverLabel.setTranslateY(item.getY());
                        this.hoverLabel.setVisible(true);
                        hoverLabel.setText(item.getName());
                        hoverLabel.toFront();
                        System.out.println("HOVER");
                        if (!item.getHoveredOver()) {
                            item.setHoveredOver();
                            tutorialTextField = 2;
                            tutorialText.setVisible(true);
                            tutorialText.setText(item.getDescription());
                        }
                    } else {
                        this.hoverLabel.setVisible(false);
                        System.out.println("NOT HOVER");
                    }
                });
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    EventHandler<MouseEvent> itemClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Item item = (Item) mouseEvent.getSource();
            pickupItem(item);
        }
    };

    private void initializeInventory() {
        inventoryListView.setItems(inventory.getItems());
        inventoryListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {

            @Override
            public ListCell<Item> call(ListView<Item> list) {
                ListCell<Item> cell = new ListCell<Item>() {

                    protected void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setDisable(true);
                            setText(null);
                            setGraphic(null);
                        } else {
                            setDisable(false);
                            setText(item.getName());
                            ImageView imageView = new ImageView();
                            imageView.setImage(item.getImage());
                            imageView.setFitWidth(65);
                            imageView.setFitHeight(65);
                            setGraphic(imageView);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void updatePowerBars() {
        double powerPercentage = power.getPower() / 100;
        System.out.println(powerPercentage);
        powerProgressBar.setProgress(powerPercentage);

        if (powerPercentage == 0.0) {
            powerImageView.setImage(pære0);
        } else if (powerPercentage <= 0.125){
            powerImageView.setImage(pære1);
        } else if (powerPercentage <= 0.25){
            powerImageView.setImage(pære2);
        } else if (powerPercentage <= 0.375){
            powerImageView.setImage(pære3);
        } else if (powerPercentage <= 0.50){
            powerImageView.setImage(pære4);
        } else if (powerPercentage <= 0.75){
            powerImageView.setImage(pære5);
        } else if (powerPercentage <= 0.875){
            powerImageView.setImage(pære6);
        } else if (powerPercentage < 0.95){
            powerImageView.setImage(pære7);
        } else {
            powerImageView.setImage(pære8);
        }
    }

    private void createRooms() {
        Room start, coal, workshop, wind1, wind2, wind3, wind4, water1, water2, water3, water4, water5, solar1, solar2, solar3, solar4;
        try {

            start = new Room("i et hus med en lyskilde, der ikke lyser. Det ligner at strømkilden er mod øst", "/Scener/startScener_3.png");
            coal = new Room("i en kælder med et kulkraftværk. Det ligner du er løbet tør for kul", "/Scener/Kul.png");
            workshop = new Room("i et værksted med tre forskellige arbejdsborde. Der er 3 døre der fører udenfor", "/Scener/Værksted.png");

            wind1 = new Room("udenfor i et område, hvor du kan mærke det blæser", "/Scener/Vind_1.png");
            wind2 = new Room("udenfor i et område, hvor der er en mild vind. Du ser nogle træer der giver læ for vinden", EnergyType.WIND, "middle", "Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da den milde vind og træerne.", "/Scener/Vind_2.png");
            wind3 = new Room("udenfor i et område, hvor det blæser. Du ser ikke noget der dækker for vinden", EnergyType.WIND, "best", "Din vindmølle genererer en rigtig god mængde energi, da det blæser og der ikke er noget som dækker.", "/Scener/Vind_3.png");
            wind4 = new Room("udenfor i et område, hvor det er en meget stærk vind", EnergyType.WIND, "worst", "Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da vinden er for stærk.", "/Scener/Vind_4.png");

            water1 = new Room("udenfor i et område, hvor du ser et vandfald", EnergyType.WATER, "best", "Din vandmølle genererer en rigtig god mængde energi, da der er en masse energi fra vandet der falder.", "/Scener/Vand_1.png");
            water2 = new Room("udenfor i et område, hvor du ser en bakke du kan gå op af", "/Scener/Vand_2.png");
            water3 = new Room("oppe på bakken, hvor du ser en flod gå gennem området", EnergyType.WATER, "middle", "Din vandmølle genererer en god mængde energi, men det er ikke optimalt, da en flod ikke er hvor der er mest energi.", "/Scener/Vand_3.png");
            water4 = new Room("oppe på bakken, hvor du ser floden gå ned til vandfaldet", "/Scener/Vand_4.png");
            water5 = new Room("oppe på bakken, hvor du ser en sø, der munder ud i en flod", EnergyType.WATER, "worst", "Din vandmølle genererer lidt energi, men det er ikke optimalt, da der ikke er meget energi i stilleliggende vand.", "/Scener/Vand_5.png");

            solar1 = new Room("udenfor i et varmt område med meget sollys", "/Scener/Sol_1.png");
            solar2 = new Room("på en flad mark med meget sol", EnergyType.SOLAR, "middle", "Din solcelle genererer en god mængde energi, men det er ikke optimalt, da en solcelle helst skal ligge på skrå.", "/Scener/Sol_2.png");
            solar3 = new Room("i en skov, hvor træerne dækker for solen", EnergyType.SOLAR, "worst", "Din solcelle genererer lidt energi, men det er ikke optimalt, da træerne skygger for solen.", "/Scener/Sol_3.png");
            solar4 = new Room("i et område med en bakke, hvor der er meget sol", EnergyType.SOLAR, "best", "Din solcelle genererer en rigtig god mængde energi, da der er en masse sol og den kan ligge med ca. 45 graders skråning på bakken. ", "/Scener/Sol_4.png");

            CraftingRoom craftingWind = new CraftingRoom("foran et grønt bord. Over bordet er der et skilt hvorpå der står \"vindenergi\".", EnergyType.WIND, windmill);
            CraftingRoom craftingWater = new CraftingRoom("foran et blåt bord. Over bordet er der et skilt hvorpå der står \"vandenergi\".", EnergyType.WATER, watermill);
            CraftingRoom craftingSun = new CraftingRoom("foran et gult bord. Over bordet er der et skilt hvorpå der står \"solenergi\".", EnergyType.SOLAR, solarpanel);

            //Udgange fra start
            start.setExit(new Exit(coal, 100, 200, 700, 200, "øst"));
            start.setExit(new Exit(workshop, 100, 200, 0, 200, "vest"));

            //Udgang fra kul
            coal.setExit(new Exit(start, 100, 200, 0, 200, "vest"));

            //Udgange fra værksted
            workshop.setExit(new Exit(wind1, 200, 100, 300, 0, "nord"));
            workshop.setExit(new Exit(water1, 100, 200, 0, 200, "vest"));
            workshop.setExit(new Exit(solar1, 200, 100, 300, 500, "syd"));
            workshop.setExit(new Exit(start, 100, 200, 700, 200, "øst"));
            workshop.setExit(new Exit(craftingWind, 200, 100, 525, 0, "vindstation"));
            workshop.setExit(new Exit(craftingSun, 200, 100, 100, 500, "solstation"));
            workshop.setExit(new Exit(craftingWater, 200, 200, 0, 0, "vandstation"));

            craftingWind.setExit(new Exit(workshop, 200, 100, 300, 500, "ud"));
            craftingSun.setExit(new Exit(workshop, 200, 100, 300, 500, "ud"));
            craftingWater.setExit(new Exit(workshop, 200, 100, 300, 500, "ud"));

            //Udgange fra vind1
            wind1.setExit(new Exit(wind2, 100, 200, 700, 100, "øst"));
            wind1.setExit(new Exit(wind3, 200, 100, 250, 0, "nord"));
            wind1.setExit(new Exit(workshop, 200, 100, 250, 500, "syd"));

            //Udgang og dropoff fra vind2
            wind2.setExit(new Exit(wind1, 100, 200, 0, 250, "vest"));
            wind2.setDropOff(new DropOff(100, 100, 550, 225, EnergyType.WIND));
            //Udgange og dropoff fra vind3
            wind3.setExit(new Exit(wind1, 200, 100, 300, 500, "syd"));
            wind3.setExit(new Exit(wind4, 100, 200, 0, 150, "vest"));
            wind3.setDropOff(new DropOff(100, 100, 540, 100, EnergyType.WIND));
            //Udgang og dropoff fra vind4
            wind4.setExit(new Exit(wind3, 100, 200, 700, 200, "øst"));
            wind4.setDropOff(new DropOff(100, 100, 60, 450, EnergyType.WIND));
            //Udgange og dropoff fra vand1
            water1.setExit(new Exit(workshop, 100, 200, 700, 200, "øst"));
            water1.setExit(new Exit(water2, 200, 100, 250, 0, "nord"));
            water1.setDropOff(new DropOff(100, 100, 25, 200, EnergyType.WATER));
            //Udgange fra vand2
            water2.setExit(new Exit(water3, 100, 200, 0, 150, "vest"));
            water2.setExit(new Exit(water1, 200, 100, 350, 500, "syd"));

            //udgange og dropoff fra vand3
            water3.setExit(new Exit(water2, 100, 200, 700, 200, "øst"));
            water3.setExit(new Exit(water4, 200, 100, 300, 500, "syd"));
            water3.setExit(new Exit(water5, 200, 100, 250, 0, "nord"));
            water3.setDropOff(new DropOff(100, 100, 10, 100, EnergyType.WATER));
            //Udgang og dropoff fra vand4
            water4.setExit(new Exit(water3, 200, 100, 300, 0, "nord"));

            //Udgang fra vand5
            water5.setExit(new Exit(water3, 200, 100, 250, 500, "syd"));
            water5.setDropOff(new DropOff(100, 100, 300, 100, EnergyType.WATER));
            //Udgange og dropoff fra sol1
            solar1.setExit(new Exit(workshop, 200, 100, 300, 0, "nord"));
            solar1.setExit(new Exit(solar2, 100, 200, 0, 200, "vest"));
            solar1.setExit(new Exit(solar3, 200, 100, 300, 500, "syd"));

            //Udgang og dropoff fra sol2
            solar2.setExit(new Exit(solar1, 100, 200, 700, 250, "øst"));
            solar2.setDropOff(new DropOff(100, 100, 50, 410, EnergyType.SOLAR));
            //Udgange og dropoff fra sol3
            solar3.setExit(new Exit(solar1, 200, 100, 300, 0, "nord"));
            solar3.setExit(new Exit(solar4, 100, 200, 0, 250, "vest"));
            solar3.setDropOff(new DropOff(100, 100, 635, 185, EnergyType.SOLAR));

            //Udgang og dropoff fra sol4
            solar4.setExit(new Exit(solar3, 100, 200, 700, 250, "øst"));
            solar4.setDropOff(new DropOff(100, 100, 100, 85, EnergyType.SOLAR));

            goRoom(start);

            //Load materials in rooms
            wind1.addItem(allItems[0]);
            wind2.addItem(allItems[1]);
            wind4.addItem(allItems[2]);

            water2.addItem(allItems[3]);
            water3.addItem(allItems[4]);
            water4.addItem(allItems[5]);

            solar1.addItem(allItems[6]);
            solar2.addItem(allItems[7]);
            solar3.addItem(allItems[8]);

//            craftingWind.addItem(allItems[9]);
//            craftingWater.addItem(allItems[10]);
//            craftingSun.addItem(allItems[11]);
            ArrayList<Room> rooms = new ArrayList<Room>(Arrays.asList(start, coal, workshop, wind1, wind2, wind3, wind4, water1, water2, water3, water4, water5, solar1, solar2, solar3, solar4, craftingWind, craftingSun, craftingWater));
            for (Room room : rooms) {

                for (Exit exit : room.getExits()) {
                    exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            goRoom(exit.getRoom());
                            if (exit.getDirection() == "vest") {
                                minimapCircle.setLayoutX(minimapCircle.getLayoutX() - 37);
                            } else if (exit.getDirection() == "øst") {
                                minimapCircle.setLayoutX(minimapCircle.getLayoutX() + 37);
                            } else if (exit.getDirection() == "nord") {
                                minimapCircle.setLayoutY(minimapCircle.getLayoutY() - 39);
                            } else if (exit.getDirection() == "syd") {
                                minimapCircle.setLayoutY(minimapCircle.getLayoutY() + 39);
                            }
                        }
                    });

                    exit.hoverProperty().addListener((obs, oldVal, newVal) -> {
                        if (newVal) {
                            this.hoverLabel.setTranslateX(exit.getX() + 5);
                            this.hoverLabel.setTranslateY(exit.getY() + exit.getHeight() / 2);
                            this.hoverLabel.setVisible(true);
                            this.hoverLabel.setText("Gå " + exit.getDirection());
                            System.out.println("HOVER");
                        } else {
                            this.hoverLabel.setVisible(false);
                            System.out.println("NOT HOVER");
                        }
                    });
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void print(String text) {
        consoleLabel.setText(text + '\n');
    }

    private void printWelcome() {
        print("Velkommen til spillet for bæredygtig energi!\nDette er et spil som vil lære dig om forskellige energiløsninger.");
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("Jeg er ikke helt med på hvad du mener...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.PLACE) {
            placeOnDropOff(command);
        } else if (commandWord == CommandWord.CRAFT) {
            //craft(command);
        } else if (commandWord == CommandWord.POWER) {
            printPower();
        }

        return wantToQuit;

    }

    private void pickupItem(Item newItem) {
        if (newItem.getWeight() + inventory.getCurrentWeight() > inventory.getWeightLimit()) {
            print("Du har ikke plads til at samle denne ting op");
            return;
        }
        inventory.addItem(newItem);
        currentRoom.removeItem(newItem);
        loadItems(currentRoom);
        print("Du har samlet " + newItem.getName().toLowerCase() + " op");
        if (newItem instanceof Product && !(currentRoom instanceof CraftingRoom)) {
            power.removePower((Product) newItem, currentRoom);
            updatePowerBars();
        }
    }

    private void printHelp() {
        System.out.println("Tak fordi du spørger om hjælp");
        System.out.println();
        System.out.println("Dine muligheder er følgende:");
        parser.showCommands();
    }

    private void goRoom(Room nextRoom) {
        if (nextRoom == null) {
            System.out.println("Det er ikke muligt!");
        } else {
            this.currentRoom = nextRoom;
            this.roomBackground.setImage(nextRoom.getBackgroundImage());
            loadExits(nextRoom);
            loadDropOffs(nextRoom);
            loadCraftButton(nextRoom);

            pane.getChildren().removeIf(node -> node instanceof ImageView);

            // If the room you're entering is a CraftingRoom, check the energyType and take any materials of that type.
            if (currentRoom instanceof CraftingRoom craftingRoom) {


                Image image = ((CraftingRoom) currentRoom).getCraftingResult().getImage();
                ImageView product = new ImageView(image);
                product.setX(100);
                product.setY(100);
                pane.getChildren().add(product);


                // Vi bruger en iterator for at undgå en ConcurrentModificationException

                int widthDiff = 115;
                System.out.println(inventory.getItems().size());
                int i = ((CraftingRoom) currentRoom).getPlacedItems().size();
                ObservableList<Item> oldInv = FXCollections.observableArrayList(inventory.getItems());
                for (Item item : oldInv) {
                    if (item instanceof Material && item.getEnergyType() == currentRoom.getEnergyType()) {
                        item.setX(100 + (widthDiff * i));
                        item.setY(420);
                        item.setMouseTransparent(true);
                        craftingRoom.placeItem((Material) item);
                        inventory.removeItem(item);
                        pane.getChildren().add(item);
                        i++;
                    }
                }

            }
            System.out.println("Loading items");
            loadItems(nextRoom);


        }
    }

    private void loadExits(Room nextRoom) {
        // Clear existing exits before adding new ones.
        pane.getChildren().removeIf(it -> it instanceof Exit);

        ArrayList<Exit> exits = nextRoom.getExits();

        pane.getChildren().addAll(exits);
    }

    private void loadItems(Room nextRoom) {
        // Clear existing items before loading in new ones
        pane.getChildren().removeIf(it -> it instanceof Item);

        ArrayList<Item> items;
        if (nextRoom instanceof CraftingRoom) {
            items = ((CraftingRoom) nextRoom).getPlacedItems();
        } else {
            items = nextRoom.getItems();
        }
        System.out.print(items);
        pane.getChildren().addAll(items);
    }

    private void loadDropOffs(Room nextRoom) {
        pane.getChildren().removeIf(it -> it instanceof DropOff);

        ArrayList<DropOff> dropOffs = nextRoom.getDropOffs();
        System.out.println(dropOffs);
        pane.getChildren().addAll(dropOffs);
        for (DropOff dropOff : dropOffs) {
            dropOff.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    DropOff dropOff = (DropOff) mouseEvent.getSource();
                    placeProduct(dropOff);
                }
            });
        }
    }

    private void loadCraftButton(Room currentRoom){
        pane.getChildren().removeIf(it -> it instanceof Button);

        if (currentRoom instanceof CraftingRoom) {
            Button craftButton = new Button("Byg");
            craftButton.setLayoutX(465);
            craftButton.setLayoutY(400);
            craftButton.setPrefWidth(100);
            craftButton.setStyle("-fx-background-color: #e3cfaf; ");

            craftButton.setOnMouseClicked(mouseEvent -> {
                craft((CraftingRoom) currentRoom);
            });

            pane.getChildren().add(craftButton);
        }
    }

    public void placeProduct(DropOff dropOff) {
        Product product = null;
        for (Item item : inventory.getItems()) {
            if (item instanceof Product) {
                if (dropOff.getEnergyType() == item.getEnergyType()) {
                    product = (Product) item;
                }
            }
        }
        if (product != null) {
            System.out.println(product);
            System.out.println(dropOff);

        inventory.removeItem(product);
        product.setX(dropOff.getX());
        product.setY(dropOff.getY());

        currentRoom.addItem(product);
        loadItems(currentRoom);
        System.out.println("Du har sat " + product.getName().toLowerCase() + " i det nuværende rum");
        power.addPower(product, currentRoom);
        updatePowerBars();
        }

//            Item productName = null;
//            if (currentRoom.getDropOffEffect() != null) {
//                for (Object item : inventoryListView.getItems()) {
//                    if (item instanceof Product) {
//                        productName = (Item) item;
//                    }
//                }
//                System.out.println(productName);
//                Item product = null;
//
//
//                for (int i = 0; i < allItems.length; i++) {
//                    if (allItems[i].getName().equals(productName.toString())) {
//                        product = allItems[i];
//                    }
//                }
//                if (product == null) {
//                    System.out.println("Genstanden blev ikke genkendt");
//                } else if (product instanceof Material) {
//                    System.out.println("Du kan ikke sætte materialer, kun produkter");
//                } else if (!inventory.getItems().contains(product)) {
//                    System.out.println("Du har ikke det nævnte produkt i dit inventar");
//                } else {
//                    inventory.removeItem(product);
//                    currentRoom.addItem(product);
//                    System.out.println("Du har sat " + product.getName() + " i det nuværende rum");
//                    power.addPower((Product) product, currentRoom);
//                }
//            }
//        } catch (NullPointerException e) {
//            System.out.println("PRODUKT IKKE VALGT");
//        }
    }

    private void craft(CraftingRoom craftingRoom) {
        if (craftingRoom.canCraft()) {
            Product product = craftingRoom.getCraftingResult();
            product.setX(595);
            product.setY(420);

            pane.getChildren().removeIf(it -> it instanceof Material);
            craftingRoom.clearPlacedItems();

            craftingRoom.placeItem(product);
            loadItems(craftingRoom);

            print("Du har bygget en " + craftingRoom.getCraftingResult().getName().toLowerCase());
            return;
        }
        print("Du mangler noget før du kan bygge en " + craftingRoom.getCraftingResult().getName().toLowerCase());;
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Giver du op?");
            return false;
        } else {
            return true;
        }
    }

    private void placeOnDropOff(Command command) {

    }

    public void printPower() {
        System.out.println("Du har " + power.getPower() + "% strøm");
    }

    public void setTutorialText() {
        if (tutorialTextField == 0) {
            tutorialText.setText("2/3 - Tryk for at se næste tip\n" +
                    "Du skal rundt i forskellige rum og samle materialer op, som du kan bygge med. Dette foregår via. værkstedsborde i lokalet til venstre, hvor du kan lave produkter, som sættes på placeringspunkter rundt omkring.");
            tutorialTextField += 1;
        }
        else if (tutorialTextField == 1) {
            tutorialText.setText("3/3 - Tryk for at se næste tip\n" +
                    "For at bevæge dig fra lokale til lokale holder du musen over en dør og trykker med musen.\n" +
                    "Du kan også samle ting op ved at trykke på dem og kan se disse ting i din inventar nede til venstre. Held og lykke!");
            tutorialTextField += 1;
        }
        else {
            tutorialText.setVisible(false);
        }
    }
}