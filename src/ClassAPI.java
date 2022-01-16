/**
 * The class returns the first and last name for the given ID
 * @autor ME
 * @version 0.0.1
*/
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class ClassAPI {
    /** HTTP Client field*/
    private final HttpClient CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    /** Uniform Resource Identifier filed*/
    private final String FURI;

    /**
     * Constructor
     * @param URI - Uniform Resource Identifier
     * @see ClassAPI#FURI
     */
    public ClassAPI(String URI){
        this.FURI = URI;
    }

    /**
     * Function parsing body of response
     * @param responseBody - body of response as string
     * @return formatted string
     */
    private String parseResponse(String responseBody){
        String[] strAr = responseBody.split(",");
        String firstName = "";
        String lastName = "";
        for (String string: strAr) {
            if (string.startsWith("\"first_name\""))
                firstName = string.replaceAll("\"", "").replaceAll("first_name:", "");
            if (string.startsWith("\"last_name\""))
                lastName = string.replaceAll("\"", "").replaceAll("last_name:", "");
        }
        return firstName + " " + lastName;
    }

    /**
     * Response function
     * @param response - contains response body and status code
     * @return formatted string or empty string
     */
    private String getResponse(HttpResponse<String> response){
        String result;
        switch (response.statusCode()){
            case 200 -> result = parseResponse(response.body());
            case 404 -> result = "User not found!";
            default -> result = "";
        }
        return result;
    }

    /**
     *
     * @param ID - identifier of person
     * @return formatted string or empty string
     * @throws Exception from function
     */
    public String getName(int ID) throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .uri(URI.create(FURI + ID))
                .build();
        return getResponse(CLIENT.send(request, HttpResponse.BodyHandlers.ofString()));
    }
}
