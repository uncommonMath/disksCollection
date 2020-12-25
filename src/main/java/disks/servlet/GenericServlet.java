package disks.servlet;

import disks.dao.PersistentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public abstract class GenericServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {request.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            readRequest(request,
                    response,
                    getUrl(),
                    getIndex(),
                    getLabels());
        } catch (NumberFormatException format) {
            showError(request, response, "Error while entering decimal number(got not a number)");
        } catch (NoSuchElementException ex) {
            showError(request, response, "Error while entering entity with relationship, which is not exist");
        } catch (Throwable throwable) {
            showError(request, response, null);
            throw throwable;
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            if (request.getParameter("read") != null) {
                readResponse(request,
                        response,
                        getUrl(),
                        getIndex(),
                        getLabels(),
                        fetchData(request));
            } else if (request.getParameter("create_request") != null) {
                create_request(request,
                        response,
                        getUrl(),
                        getIndex(),
                        getLabels());
            } else if (request.getParameter("create_response") != null) {
                insert(request);
                readResponse(request,
                        response,
                        getUrl(),
                        getIndex(),
                        getLabels(),
                        fetchData(request));
            } else if (request.getParameter("update_request") != null) {
                update_request(request,
                        response,
                        getUrl(),
                        getIndex(),
                        getLabels(),
                        fetchData(request));
            } else if (request.getParameter("update_response") != null) {
                update(request);
                readResponse(request,
                        response,
                        getUrl(),
                        getIndex(),
                        getLabels(),
                        fetchData(request));
            } else if (request.getParameter("delete") != null) {
                delete(request);
                readResponse(request,
                        response,
                        getUrl(),
                        getIndex(),
                        getLabels(),
                        fetchData(request));
            } else {
                showError(request, response, "A mistake");
            }
        } catch (NumberFormatException format) {
            showError(request, response, "Error while entering decimal number(got not a number)");
        } catch (NoSuchElementException ex) {
            showError(request, response, "Error while entering entity with relationship, which is not exist");
        } catch (PersistentException persistentException) {
            showError(request, response, "Database error");
        } catch (Throwable throwable) {
            showError(request, response, null);
        }
    }

    protected abstract String getUrl();
    protected abstract String[] getIndex();
    protected abstract String[] getLabels();
    protected abstract String[][] fetchData(HttpServletRequest request) throws PersistentException;
    protected abstract void insert(HttpServletRequest request) throws PersistentException;
    protected abstract void update(HttpServletRequest request) throws PersistentException;
    protected abstract void delete(HttpServletRequest request) throws PersistentException;

    private void readRequest(HttpServletRequest request,
                             HttpServletResponse response,
                             String url,
                             String[] index,
                             String[] labels) throws ServletException, IOException {
        request.setAttribute("url", url);
        request.setAttribute("index", index);
        request.setAttribute("labels", labels);
        request.setAttribute("type", "read");
        request.setAttribute("submitText", "Find");
        request.setAttribute("id", -1);
        request.setAttribute("title", "Disks Collection");
        getServletContext().getRequestDispatcher("/jsp/form.jsp").forward(request, response);
    }
    private void readResponse(HttpServletRequest request,
                             HttpServletResponse response,
                             String url, String[] index,
                             String[] labels,
                             String[][] data) throws ServletException, IOException {
        request.setAttribute("url", url);
        request.setAttribute("index", index);
        request.setAttribute("labels", labels);
        request.setAttribute("data", data);
        request.setAttribute("title", "Disks Collection");
        getServletContext().getRequestDispatcher("/jsp/view.jsp").forward(request, response);
    }
    private void create_request(HttpServletRequest request,
                                HttpServletResponse response,
                                String url,
                                String[] index,
                                String[] labels) throws ServletException, IOException {
        request.setAttribute("url", url);
        request.setAttribute("index", index);
        request.setAttribute("labels", labels);
        request.setAttribute("type", "create_response");
        request.setAttribute("submitText", "Create");
        request.setAttribute("id", -1);
        request.setAttribute("title", "Disks Collection");
        getServletContext().getRequestDispatcher("/jsp/form.jsp").forward(request, response);
    }
    private void update_request(HttpServletRequest request,
                             HttpServletResponse response,
                             String url,
                             String[] index,
                             String[] labels,
                             String[][] data) throws ServletException, IOException {
        request.setAttribute("url", url);
        request.setAttribute("index", index);
        request.setAttribute("labels", labels);
        request.setAttribute("type", "update_response");
        request.setAttribute("submitText", "Edit");
        request.setAttribute("data", data[0]);
        request.setAttribute("title", "Disks Collection");
        getServletContext().getRequestDispatcher("/jsp/form.jsp").forward(request, response);
    }
    private void showError(HttpServletRequest request,
                           HttpServletResponse response,
                           String message) throws ServletException, IOException {
        request.setAttribute("error", true);
        if (message != null) {
            request.setAttribute("message", message);
        }
        getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
    }
}