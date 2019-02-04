package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller {
	@FXML
	private MenuItem openImage, greyScale, colorScale, exit, resize;
		
	@FXML
	private ImageView imageViewBefore, imageView, redView, greenView, blueView;
	
	@FXML
	private Label nameLabel, sizeLabel, pathLabel, name, size, path;
		
    @FXML
	private AnchorPane ap;
	
	@FXML
	private Image image;
	
	@FXML
	private Button applyBtn, redBtn, greenBtn, blueBtn;
	
	@FXML
	private CheckBox red, green, blue;
	
	ColorAdjust ca = new ColorAdjust();
	
	BufferedImage bufferedImage;
	
	FileChooser fc;
	
	File selectedFile;
	
	WritableImage writableImage, rwi, gwi, bwi;
	PixelWriter pixelWriter, rpw, gpw, bpw;

	
	
	public void EventMenuItem(ActionEvent e)
	{
		fc = new FileChooser();		
		fc.setInitialDirectory(new File("C:\\Users\\asus\\Pictures\\Saved Pictures"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Images", "*.jpg", "*.png", "*.JPG", "*.PNG"));
				
		selectedFile = fc.showOpenDialog(null);
		
		if (selectedFile != null)
		{			
			try 
			{				
				bufferedImage = ImageIO.read(selectedFile);
								
				image = SwingFXUtils.toFXImage(bufferedImage, null);
				
				imageView.setFitWidth(ap.getWidth());
				imageViewBefore.setFitWidth(ap.getWidth());
				
				imageView.setEffect(ca);;
				imageView.setImage(image);	
				imageViewBefore.setImage(image);
								
				name.setText("Name");
				size.setText("Size");
				path.setText("Path");
				
				nameLabel.setText(selectedFile.getName());
				sizeLabel.setText(String.valueOf(selectedFile.length()) + " bytes");
				pathLabel.setText(selectedFile.getPath());
				
			} 
			catch (IOException ex) 
			{
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);			
            }			
		}
		else
		{
			System.out.println("file is not valid");
		}
	}
	
	public void EventGreyImage(ActionEvent event)
	{
		ca.setSaturation(-1);
		imageView.setEffect(ca);
	}
	
	public void EventColorImage(ActionEvent event)
	{
		ca.setSaturation(0);
		imageView.setEffect(ca);
		imageView.setImage(image);
	}
		
	public void btnEvent(ActionEvent event)
	{		
		writableImage = new WritableImage((int) bufferedImage.getWidth(), (int) bufferedImage.getHeight());
		pixelWriter = writableImage.getPixelWriter();
		
		        
        for (int readY = 0; readY < bufferedImage.getHeight(); readY++)
        {
        	for (int readX = 0; readX < bufferedImage.getWidth(); readX++)
        	{
        		int p = bufferedImage.getRGB(readX, readY);
        		
        		int a = (p>>24)&0xff;
        		int g = 0;
        		int r = 0;
        		int b = 0;
        		
        		if (red.isSelected())
        		{
        			r = (p>>8)&0xff;
        		}
        		if (green.isSelected())
        		{
        			g = (p>>8)&0xff;
        		}
        		if (blue.isSelected())
        		{
        			b = (p>>8)&0xff;
        		}
        		
        		p = (a<<24) | (r << 16) | (g << 8) | b;
        		
        		pixelWriter.setArgb(readX, readY, p);
        	}
        }        
        imageView.setImage(writableImage);
	}	
	
	public void redButtonAction(ActionEvent event)
	{
		writableImage = new WritableImage((int) bufferedImage.getWidth(), (int) bufferedImage.getHeight());
		pixelWriter = writableImage.getPixelWriter();
		
		for (int readY = 0; readY < bufferedImage.getHeight(); readY++)
        {
        	for (int readX = 0; readX < bufferedImage.getWidth(); readX++)
        	{
        		int p = bufferedImage.getRGB(readX, readY);
        		
        		int a = (p>>24)&0xff;
        		int r = (p>>8)&0xff;
        		
        		p = (a<<24) | (r << 16) | (0 << 8) | 0;
        		
        		pixelWriter.setArgb(readX, readY, p);
        	}
        }        
        redView.setImage(writableImage);
        redView.setFitWidth(ap.getWidth());
        
        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(redView);
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Red channel");
		stage.show();
	}
	
	public void greenButtonAction(ActionEvent event)
	{
		writableImage = new WritableImage((int) bufferedImage.getWidth(), (int) bufferedImage.getHeight());
		pixelWriter = writableImage.getPixelWriter();
		
		for (int readY = 0; readY < bufferedImage.getHeight(); readY++)
        {
        	for (int readX = 0; readX < bufferedImage.getWidth(); readX++)
        	{
        		int p = bufferedImage.getRGB(readX, readY);
        		
        		int a = (p>>24)&0xff;
        		int g = (p>>8)&0xff;
        		
        		p = (a<<24) | (0 << 16) | (g << 8) | 0;
        		
        		pixelWriter.setArgb(readX, readY, p);
        	}
        }        
        greenView.setImage(writableImage);
        greenView.setFitWidth(ap.getWidth());
        
        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(greenView);
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Green channel");
		stage.show();
	}
	
	public void blueButtonAction(ActionEvent event)
	{
		writableImage = new WritableImage((int) bufferedImage.getWidth(), (int) bufferedImage.getHeight());
		pixelWriter = writableImage.getPixelWriter();
		
		for (int readY = 0; readY < bufferedImage.getHeight(); readY++)
        {
        	for (int readX = 0; readX < bufferedImage.getWidth(); readX++)
        	{
        		int p = bufferedImage.getRGB(readX, readY);
        		
        		int a = (p>>24)&0xff;
        		int b = (p>>8)&0xff;
        		
        		p = (a<<24) | (0 << 16) | (0 << 8) | b;
        		
        		pixelWriter.setArgb(readX, readY, p);
        	}
        }        
        blueView.setImage(writableImage);
        blueView.setFitWidth(ap.getWidth());
        
        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(blueView);
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Blue channel");
		stage.show();
	}
	
	public void exitAction(ActionEvent e)
	{
		Platform.exit();
	}
	
	public void adjustSize(ActionEvent e)
	{
		imageView.setFitWidth(ap.getWidth());
		imageViewBefore.setFitWidth(ap.getWidth());
	}
}
