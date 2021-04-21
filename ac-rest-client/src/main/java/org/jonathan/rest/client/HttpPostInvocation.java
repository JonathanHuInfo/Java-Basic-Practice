package org.jonathan.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.jonathan.rest.core.DefaultResponse;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static org.jonathan.rest.util.URLUtils.DEFAULT_ENCODING;

public class HttpPostInvocation implements Invocation {

    private final URI uri;

    private final URL url;

    private final MultivaluedMap<String, Object> headers;

    private final Entity<?> entity;

    HttpPostInvocation(URI uri, MultivaluedMap<String, Object> headers, Entity<?> entity) {
        this.uri = uri;
        this.headers = headers;
        this.entity = entity;
        try {
            this.url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Invocation property(String s, Object o) {
        return this;
    }

    @Override
    public Response invoke() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.POST);
            connection.setDoOutput(true);
            setRequestHeaders(connection);
            writeEntity(connection, this.entity);
            // TODO Set the cookies
            int statusCode = connection.getResponseCode();
//            Response.ResponseBuilder responseBuilder = Response.status(statusCode);
//
//            responseBuilder.build();
            DefaultResponse response = new DefaultResponse();
            response.setConnection(connection);
            response.setStatus(statusCode);
            return response;
//            Response.Status status = Response.Status.fromStatusCode(statusCode);
//            switch (status) {
//                case Response.Status.OK:
//
//                    break;
//                default:
//                    break;
//            }

        } catch (IOException e) {
            // TODO Error handler
        }
        return null;
    }

    private void setRequestHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (Object headerValue : entry.getValue()) {
                connection.setRequestProperty(headerName, headerValue.toString());
            }
        }
    }

    private <E> void writeEntity(HttpURLConnection connection, Entity<E> entity) {
        E entityValue = entity.getEntity();
        try {
            OutputStream outputStream = connection.getOutputStream();
            if(entity.getMediaType().equals(MediaType.TEXT_PLAIN_TYPE)){
                IOUtils.write((String) entityValue, outputStream, DEFAULT_ENCODING);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(new OutputStreamWriter(outputStream), entityValue);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T invoke(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T invoke(GenericType<T> genericType) {
        return null;
    }

    @Override
    public Future<Response> submit() {
        return null;
    }

    @Override
    public <T> Future<T> submit(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> Future<T> submit(GenericType<T> genericType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> invocationCallback) {
        return null;
    }
}
