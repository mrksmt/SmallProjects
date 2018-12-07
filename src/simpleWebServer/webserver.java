package simpleWebServer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class webserver {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8765), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                StringBuilder builder = new StringBuilder();

                builder.append("<h1>URI: ").append(exchange.getRequestURI()).append("</h1>");

                Headers headers = exchange.getRequestHeaders();
                for (String header : headers.keySet()) {
                    builder.append("<p>").append(header).append("=")
                            .append(headers.getFirst(header)).append("</p>");
                }

                byte[] bytes = builder.toString().getBytes();
                System.out.println(builder.toString());
                exchange.sendResponseHeaders(200, bytes.length);

                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            }
        });
        server.start();

    }
}

