package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.DTOFlowDefinitionInRoles;
import dto.DTOFlowsDefinitionInRoles;
import dto.DTORolesList;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import json.DTOFlowsDefinitionInRolesDeserializer;
import systemengine.systemengine;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "FlowsInRolesServlet", urlPatterns = "/flows-in-roles")
public class FlowsInRolesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roles = request.getParameter("roles_list");
        response.setContentType("application/json");
        System.out.println("FlowsInRolesServlet: " + roles);
        if (roles!="") {
            List<String> rolesList = Arrays.asList(roles.split(","));
            try (PrintWriter out = response.getWriter()) {

                systemengine system = ServletUtils.getSystemEngine(getServletContext());
                DTOFlowsDefinitionInRoles dtoFlowDefinitionInRoles = system.getDtoFlowsDefinition(rolesList);
                System.out.println("FlowsInRolesServlet: " + dtoFlowDefinitionInRoles.getFlowsDefinitionInRoles().size());
                /////////////נופל כאן בגלל שהוא לא מצליח להמיר את הטייפ של הפלואים לג'ייסון
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(DTOFlowsDefinitionInRoles.class, new DTOFlowsDefinitionInRolesDeserializer())
                        .setPrettyPrinting()
                        .create();

                System.out.println("FlowsInRolesServlet: " + dtoFlowDefinitionInRoles.toString());
                String json = gson.toJson(dtoFlowDefinitionInRoles);
                System.out.println("FlowsInRolesServletJson: " + json);
                out.println(json);
                out.flush();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
