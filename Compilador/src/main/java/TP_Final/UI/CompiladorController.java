package TP_Final.UI;

import TP_Final.observable.Observer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import TP_Final.compilador.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

//imports drag image
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class CompiladorController implements Observer, Initializable {
	//FXML CompiladorInterfaz.fxml
	@FXML private VBox anchorPane;
	@FXML private TabPane tabPane;
		
	//Tabs
	@FXML private Tab tabCodigoFuente;
	@FXML private Tab tabVerTS;
	@FXML private Tab tabVerAST;
	@FXML private Tab tabVerASM;
	@FXML private Tab tabEjecutarCodigo;
	
	//VBoxs
	@FXML private VBox vBoxCodigoFuente;
	@FXML private VBox vBoxVerTS;
	@FXML private VBox vBoxVerAST;
	@FXML private VBox vBoxVerASM;
	@FXML private VBox vBoxEjecutarCodigo;
	
	//Stack Pane de cada codeArea
	@FXML private StackPane paneCodigoFuente;
	@FXML private StackPane paneVerTS;
	@FXML private StackPane paneVerASM;
	@FXML private StackPane paneEjecutarCodigo;
	
	//toolbars
	@FXML private ToolBar toolBarCodigoFuente;
	@FXML private ToolBar toolBarVerTS;
	@FXML private ToolBar toolBarVerAST;
	@FXML private ToolBar toolBarVerASM;
	@FXML private ToolBar toolBarEjecutarCodigo;
	
	//Botones de toolbars
	@FXML private Button btnCargarCodigoFuente; //Cargar txt con codigo fuente
	@FXML private Button btnVerTS;
	@FXML private Button btnVerAST;
	@FXML private Button btnVerASM;
	@FXML private Button btnEjecutarCodigo;
	
	//CodeAreas
	private CodeArea codeAreaCodigoFuente = new RichTextArea().getCodeArea();
	private CodeArea codeAreaTS = new RichTextArea().getCodeArea();
	private CodeArea codeAreaASM = new RichTextArea().getCodeArea();
	private CodeArea codeAreaEjecutarCodigo = new RichTextArea().getCodeArea();
	
	//image AST
	@FXML private ImageView imageAST;
	
	//Variables para zoom imageView
	@FXML private StackPane paneVerAST;
	@FXML private ScrollPane scrollPaneVerAST;
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);
    private final Group group = new Group();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabPane.prefWidthProperty().bind(anchorPane.widthProperty());
		tabPane.prefHeightProperty().bind(anchorPane.heightProperty());
		
		paneCodigoFuente.prefHeightProperty().bind(vBoxCodigoFuente.heightProperty());
		paneVerTS.prefHeightProperty().bind(vBoxVerTS.heightProperty());
		paneVerAST.prefHeightProperty().bind(vBoxVerAST.heightProperty());
		paneVerASM.prefHeightProperty().bind(vBoxVerASM.heightProperty());
		paneEjecutarCodigo.prefHeightProperty().bind(vBoxEjecutarCodigo.heightProperty());
		
		paneCodigoFuente.getChildren().add(new VirtualizedScrollPane<>(codeAreaCodigoFuente));
		paneVerTS.getChildren().add(new VirtualizedScrollPane<>(codeAreaTS));
		paneVerASM.getChildren().add(new VirtualizedScrollPane<>(codeAreaASM));
		paneEjecutarCodigo.getChildren().add(new VirtualizedScrollPane<>(codeAreaEjecutarCodigo));
		
		codeAreaTS.setEditable(false);
		codeAreaASM.setEditable(false);
		codeAreaEjecutarCodigo.setEditable(false);
		
		try {			
			InputStream stream = new FileInputStream("./noAST.png");
			
		    Image image = new Image(stream);
		    //Setting image to the image view
		    imageAST.setImage(image);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		//scrollPaneVerAST.setPannable(true);
		//scrollPaneVerAST.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		//scrollPaneVerAST.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		scrollPaneVerAST.setPannable(true);
		scrollPaneVerAST.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPaneVerAST.setVbarPolicy(ScrollBarPolicy.NEVER);
        AnchorPane.setTopAnchor(scrollPaneVerAST, 1.0d);
        AnchorPane.setRightAnchor(scrollPaneVerAST, 1.0d);
        AnchorPane.setBottomAnchor(scrollPaneVerAST, 1.0d);
        AnchorPane.setLeftAnchor(scrollPaneVerAST, 1.0d);

        group.getChildren().add(imageAST);
        
        // create canvas
        PanAndZoomPane panAndZoomPane = new PanAndZoomPane();
        zoomProperty.bind(panAndZoomPane.myScale);
        deltaY.bind(panAndZoomPane.deltaY);
        panAndZoomPane.getChildren().add(group);

        SceneGestures sceneGestures = new SceneGestures(panAndZoomPane);

        scrollPaneVerAST.setContent(panAndZoomPane);
        panAndZoomPane.toBack();
        scrollPaneVerAST.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        scrollPaneVerAST.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scrollPaneVerAST.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scrollPaneVerAST.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
	}
	
	//Contructor
	public CompiladorController(){
		Main.getInstance().addObserver(this);
	}
	
	//Funciones	
	public void cargarCodigoFuente() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		String data = "";
		if(selectedFile != null) {
			try {
				File myObj = new File(selectedFile.getAbsolutePath());
				Scanner myReader = new Scanner(myObj);
				
				while (myReader.hasNextLine()) {
					data += myReader.nextLine() + "\n";
				}
				myReader.close();
				codeAreaCodigoFuente.replaceText(data);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void verTS() {
		Main.getInstance().verTS(codeAreaCodigoFuente.getText());
	}
	
	public void verAST() {
		try {
			Main.getInstance().verAST(codeAreaCodigoFuente.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void verASM() {
		Main.getInstance().verASM(codeAreaCodigoFuente.getText());
	}
	
	public void ejecutarCodigo() {
		Main.getInstance().ejecutarCodigo(codeAreaCodigoFuente.getText());
	}

	// Respuestas Controlador
	@Override
	public void update(int op, String log) {		
		switch (op)
		{
			case 0:// Respuesta funcion "verTS"
				updateTS(log);
				break;
			case 1:// Respuesta funcion "verAST" exitosa
				changeImageAST();
				break;
			case 2:// Respuesta funcion "verAST" fallida 
				changeImageError();
				break;
			case 3:// Respuesta funcion "verASM"
				updateASM(log);
				break;
			case 4:// Respuesta funcion "ejecutarCodigo"
				updateEjecutarCodigo(log);
				break;
		}
	}

	public void updateTS(String log) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		codeAreaTS.replaceText(log);
	}
	
	public void changeImageAST() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//creating the image object
	    InputStream stream;
		try {
			stream = new FileInputStream("./AST.png");
			
		    Image image = new Image(stream);
		    //Setting image to the image view
		    imageAST.setImage(image);
		    
	        //group.getChildren().add(imageAST);
	        PanAndZoomPane panAndZoomPane = new PanAndZoomPane();
	        zoomProperty.bind(panAndZoomPane.myScale);
	        deltaY.bind(panAndZoomPane.deltaY);
	        panAndZoomPane.getChildren().add(group);

	        SceneGestures sceneGestures = new SceneGestures(panAndZoomPane);
	        scrollPaneVerAST.setContent(panAndZoomPane);
	        panAndZoomPane.toBack();
	        scrollPaneVerAST.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
	        scrollPaneVerAST.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
	        scrollPaneVerAST.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
	        scrollPaneVerAST.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
		} catch (FileNotFoundException e) {
			try {
				stream = new FileInputStream("./noAST.png");
				
			    Image image = new Image(stream);
			    //Setting image to the image view
			    imageAST.setImage(image);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void changeImageError() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//creating the image object
	    InputStream stream;
		try {
			stream = new FileInputStream("./noAST.png");
			
		    Image image = new Image(stream);
		    //Setting image to the image view
		    imageAST.setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void updateASM(String log) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(log.equals("")) {
			String data = "";
			try {
				File myObj = new File("./Final.asm");
				Scanner myReader = new Scanner(myObj);
				
				while (myReader.hasNextLine()) {
					data += myReader.nextLine() + "\n";
				}
				myReader.close();
				codeAreaASM.replaceText(data);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}			
		} else {
			codeAreaASM.replaceText(log);
		}

	}
	
	private void updateEjecutarCodigo(String log) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(log.equals("")) {
			String cmd = "START /D \"C:\\Program Files (x86)\\GUI Turbo Assembler\\BIN\" TASM Final.asm";
			
			//START /D "C:\Program Files (x86)\GUI Turbo Assembler\BIN" TASM Final.asm
			//START /D "C:\Program Files (x86)\GUI Turbo Assembler\BIN" TLINK Final
			//START /D "C:\Program Files (x86)\GUI Turbo Assembler\BIN" TLINK /3 Final.obj numbers.obj /v /s /m
			//START /D "C:\Program Files (x86)\GUI Turbo Assembler\BIN" Final.exe
			
			/*try {
				Runtime.getRuntime().exec(cmd);
			} catch (IOException e) {
				System.out.println("FALLO EL CMD de TASM");
				e.printStackTrace();
			}*/
			
			codeAreaEjecutarCodigo.replaceText("Falta implementacion.");
			
		} else {
			codeAreaEjecutarCodigo.replaceText(log);
		}
		
	}

	//Clases para zoom image
	class PanAndZoomPane extends Pane {

	    public static final double DEFAULT_DELTA = 1.3d;
	    DoubleProperty myScale = new SimpleDoubleProperty(1.0);
	    public DoubleProperty deltaY = new SimpleDoubleProperty(0.0);
	    private Timeline timeline;


	    public PanAndZoomPane() {

	        this.timeline = new Timeline(60);

	        // add scale transform
	        scaleXProperty().bind(myScale);
	        scaleYProperty().bind(myScale);
	    }


	    public double getScale() {
	        return myScale.get();
	    }

	    public void setScale( double scale) {
	        myScale.set(scale);
	    }

	    public void setPivot( double x, double y, double scale) {
	        // note: pivot value must be untransformed, i. e. without scaling
	        // timeline that scales and moves the node
	        timeline.getKeyFrames().clear();
	        timeline.getKeyFrames().addAll(
	            new KeyFrame(Duration.millis(200), new KeyValue(translateXProperty(), getTranslateX() - x)),
	            new KeyFrame(Duration.millis(200), new KeyValue(translateYProperty(), getTranslateY() - y)),
	            new KeyFrame(Duration.millis(200), new KeyValue(myScale, scale))
	        );
	        timeline.play();

	    }

	    public void fitWidth () {
	        double scale = getParent().getLayoutBounds().getMaxX()/getLayoutBounds().getMaxX();
	        double oldScale = getScale();

	        double f = scale - oldScale;

	        double dx = getTranslateX() - getBoundsInParent().getMinX() - getBoundsInParent().getWidth()/2;
	        double dy = getTranslateY() - getBoundsInParent().getMinY() - getBoundsInParent().getHeight()/2;

	        double newX = f*dx + getBoundsInParent().getMinX();
	        double newY = f*dy + getBoundsInParent().getMinY();

	        setPivot(newX, newY, scale);

	    }

	    public void resetZoom () {
	        double scale = 1.0d;

	        double x = getTranslateX();
	        double y = getTranslateY();

	        setPivot(x, y, scale);
	    }

	    public double getDeltaY() {
	        return deltaY.get();
	    }
	    public void setDeltaY( double dY) {
	        deltaY.set(dY);
	    }
	}


	/**
	 * Mouse drag context used for scene and nodes.
	 */
	class DragContext {

	    double mouseAnchorX;
	    double mouseAnchorY;

	    double translateAnchorX;
	    double translateAnchorY;

	}

	/**
	 * Listeners for making the scene's canvas draggable and zoomable
	 */
	public class SceneGestures {

	    private DragContext sceneDragContext = new DragContext();

	    PanAndZoomPane panAndZoomPane;

	    public SceneGestures( PanAndZoomPane canvas) {
	        this.panAndZoomPane = canvas;
	    }

	    public EventHandler<MouseEvent> getOnMouseClickedEventHandler() {
	        return onMouseClickedEventHandler;
	    }

	    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
	        return onMousePressedEventHandler;
	    }

	    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
	        return onMouseDraggedEventHandler;
	    }

	    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
	        return onScrollEventHandler;
	    }

	    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent event) {

	            sceneDragContext.mouseAnchorX = event.getX();
	            sceneDragContext.mouseAnchorY = event.getY();

	            sceneDragContext.translateAnchorX = panAndZoomPane.getTranslateX();
	            sceneDragContext.translateAnchorY = panAndZoomPane.getTranslateY();

	        }

	    };

	    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent event) {

	            panAndZoomPane.setTranslateX(sceneDragContext.translateAnchorX + event.getX() - sceneDragContext.mouseAnchorX);
	            panAndZoomPane.setTranslateY(sceneDragContext.translateAnchorY + event.getY() - sceneDragContext.mouseAnchorY);

	            event.consume();
	        }
	    };

	    /**
	     * Mouse wheel handler: zoom to pivot point
	     */
	    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

	        @Override
	        public void handle(ScrollEvent event) {

	            double delta = PanAndZoomPane.DEFAULT_DELTA;

	            double scale = panAndZoomPane.getScale(); // currently we only use Y, same value is used for X
	            double oldScale = scale;

	            panAndZoomPane.setDeltaY(event.getDeltaY()); 
	            if (panAndZoomPane.deltaY.get() < 0) {
	                scale /= delta;
	            } else {
	                scale *= delta;
	            }

	            double f = (scale / oldScale)-1;
	            double dx = (event.getX() - (panAndZoomPane.getBoundsInParent().getWidth()/2 + panAndZoomPane.getBoundsInParent().getMinX()));
	            double dy = (event.getY() - (panAndZoomPane.getBoundsInParent().getHeight()/2 + panAndZoomPane.getBoundsInParent().getMinY()));

	            panAndZoomPane.setPivot(f*dx, f*dy, scale);

	            event.consume();

	        }
	    };

	    /**
	     * Mouse click handler
	     */
	    private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	            if (event.getButton().equals(MouseButton.PRIMARY)) {
	                if (event.getClickCount() == 2) {
	                    panAndZoomPane.resetZoom();
	                }
	            }
	            if (event.getButton().equals(MouseButton.SECONDARY)) {
	                if (event.getClickCount() == 2) {
	                    panAndZoomPane.fitWidth();
	                }
	            }
	        }
	    };
	}
	
}