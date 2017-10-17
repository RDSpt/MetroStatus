package application.javafx;

import com.google.gson.*;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
	
	@FXML
	Label amarelaStatus, azulStatus, verdeStatus, vermelhaStatus, timestamp;
	
	@FXML
	AnchorPane amarelaImg, azulImg, verdeImg, vermelhaImg;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	public void initialize() {
		
		getInfo();
		final Calendar[] cal = {Calendar.getInstance()};
		timestamp.setText(sdf.format(cal[0].getTime()));
		Timeline refresh = new Timeline(new KeyFrame(Duration.minutes(1), event -> {
			getInfo();
			cal[0] = Calendar.getInstance();
			timestamp.setText(sdf.format(cal[0].getTime()));
		}));
		refresh.setCycleCount(Timeline.INDEFINITE);
		refresh.play();
		
	}
	
	private void getInfo() {
		
		try {
			URL url = new URL("http://app.metrolisboa.pt/status/getLinhas.php");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String            inputLine = in.readLine();
			ArrayList<String> info      = parse(inputLine);
			amarelaStatus.setText(info.get(0));
			azulStatus.setText(info.get(1));
			verdeStatus.setText(info.get(2));
			vermelhaStatus.setText(info.get(3));
			graphicStatus(amarelaImg, info.get(0));
			graphicStatus(azulImg, info.get(1));
			graphicStatus(verdeImg, info.get(2));
			graphicStatus(vermelhaImg, info.get(3));
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> parse(String input) {
		
		JsonElement jelement = new JsonParser().parse(input);
		System.out.println(jelement);
		JsonObject        jarray         = jelement.getAsJsonObject();
		ArrayList<String> info           = new ArrayList<>();
		String            statusAmarela  = jarray.get("amarela").toString().replace("\"", "");
		String            statusAzul     = jarray.get("azul").toString().replace("\"", "");
		String            statusVerde    = jarray.get("verde").toString().replace("\"", "");
		String            statusVermelha = jarray.get("vermelha").toString().replace("\"", "");
		String            msgAmarela     = jarray.get("tipo_msg_am").toString().replace("\"", "");
		String            msgAzul        = jarray.get("tipo_msg_az").toString().replace("\"", "");
		String            msgVerde       = jarray.get("tipo_msg_vd").toString().replace("\"", "");
		String            msgVermelha    = jarray.get("tipo_msg_vm").toString().replace("\"", "");
		info.add(statusAmarela);
		info.add(statusAzul);
		info.add(statusVerde);
		info.add(statusVermelha);
		info.add(msgAmarela);
		info.add(msgAzul);
		info.add(msgVerde);
		info.add(msgVermelha);
		return info;
	}
	
	private void graphicStatus(AnchorPane img, String status) {
		
		if (status.contains("Ok")) {
			try {
				img.getChildren().add(new StackPane(img.getChildren().get(0), new ImageView
						(new Image
								("/graphics/check.png",
										10, 10,
										true,
										true))));
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
		}
	}
}
