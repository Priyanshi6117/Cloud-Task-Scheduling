package CloudProject;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class CloudWebServer {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "10000"));
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

        server.createContext("/", exchange -> {
            String response = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Cloud Task Scheduling</title>
                        <style>
                            body { font-family: Arial; text-align: center; margin: 80px; }
                            a { display: inline-block; padding: 12px 20px; background: #2563eb;
                                color: white; text-decoration: none; border-radius: 6px; }
                        </style>
                    </head>
                    <body>
                        <h1>Cloud Task Scheduling</h1>
                        <p>Java CloudSim Plus application is running.</p>
                        <p>Genetic Algorithm and Auto Scaling Project</p>
                        <a href="/run">Run CloudSim Simulation</a>
                    </body>
                    </html>
                    """;

            sendResponse(exchange, 200, response, "text/html; charset=UTF-8");
        });

        server.createContext("/run", exchange -> {
            String response;
            int status = 200;
            java.io.PrintStream original = System.out;

            try {
                java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();

                try (java.io.PrintStream capture =
                             new java.io.PrintStream(buffer, true, StandardCharsets.UTF_8)) {
                    synchronized (CloudWebServer.class) {
                        System.setOut(capture);
                        try {
                            BasicCloudSimulation.main(new String[0]);
                        } finally {
                            System.setOut(original);
                        }
                    }
                }

                String output = buffer.toString(StandardCharsets.UTF_8)
                        .replace("&", "&amp;")
                        .replace("<", "&lt;")
                        .replace(">", "&gt;");

                response = "<html><body style='font-family:Arial;margin:40px'>"
                        + "<h1>CloudSim Simulation Results</h1><pre>"
                        + output
                        + "</pre><a href='/'>Home</a></body></html>";

            } catch (Exception e) {
                System.setOut(original);
                status = 500;
                response = "<html><body><h1>Simulation failed</h1><pre>"
                        + e.getMessage()
                        + "</pre><a href='/'>Home</a></body></html>";
            }

            sendResponse(exchange, status, response, "text/html; charset=UTF-8");
        });

        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        server.start();

        System.out.println("Cloud Web Server running on port " + port);
    }

    private static void sendResponse(
            com.sun.net.httpserver.HttpExchange exchange,
            int status,
            String response,
            String contentType) throws IOException {

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, bytes.length);

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}