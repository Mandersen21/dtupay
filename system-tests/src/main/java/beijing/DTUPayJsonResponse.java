package beijing;

import com.mashape.unirest.http.JsonNode;

public class DTUPayJsonResponse extends DTUPayResponse {
	
	JsonNode jsonBody;

	public DTUPayJsonResponse(int responseCode, String responseText) {
		super(responseCode, responseText);
		// TODO Auto-generated constructor stub
	}

	public DTUPayJsonResponse(int status, JsonNode body) {
		super(status,body.toString());
		jsonBody = body;
		
		// TODO Auto-generated constructor stub
	}

	public JsonNode getJsonBody() {
		return jsonBody;
	}

	public void setJsonBody(JsonNode jsonBody) {
		this.jsonBody = jsonBody;
	}

	
}
