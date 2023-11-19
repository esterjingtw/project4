package ds.project4;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;
import com.google.gson.Gson;


public class JokeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String jokeType = request.getParameter("type");
        if (jokeType == null || jokeType.isEmpty()) {
            jokeType = "Any";
        }

        try {
            URL url = new URL("https://v2.jokeapi.dev/joke/" + jokeType);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Parse the joke
            Gson gson = new Gson();
            Joke joke = gson.fromJson(content.toString(), Joke.class);

            // Log the request to MongoDB
            Document log = new Document("type", jokeType)
                    .append("setup", joke.getSetup())
                    .append("delivery", joke.getDelivery());
            MongodbConnection.insertDocument(log, "joke_logs");

            request.setAttribute("joke", gson.toJson(joke));

            // Forward the request back to the JSP page
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/joke.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // Handle the error and forward to JSP with an error message
            request.setAttribute("error", "Unable to fetch joke: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/joke.jsp");
            dispatcher.forward(request, response);        }
    }
}
