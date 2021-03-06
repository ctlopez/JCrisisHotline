/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Accessors.ResourceAccessor;
import Beans.Resource;
import Beans.ResourcePagBean;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This Servlet uses a resourceId parameter and switch structure to handle
 * various requests
 *
 * @author Chrsitain Lopez
 *
 * Updated: 2017/04/18 By: Alissa Duffy Standardized Commenting.
 */
public class ResourceHandler extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //int resourceId, String categories, String name, String phone, String addressOne, String addressTwo, String city, String territory, String country, String postalCode, String email, String description
        String action = request.getParameter("action");
        if (null == action) {
            action = "list"; // This will be default
        }
        HttpSession session = request.getSession();

        switch (action) {
            /**
             * Christian Lopez
             */
            case "create":

                int id;
                try {
                    id = Integer.parseInt(request.getParameter("resourceId"));
                } catch (Exception e) {
                    id = 0;
                }

                String categoryCSV = request.getParameter("resourceCategory");
                String[] categoryArray = categoryCSV.split(",");
                try {
                    for (String category : categoryArray) {
                        String temp = category.trim();
                        temp = temp;
                        if (!(ResourceAccessor.getOccurances(temp) > 0)) {
                            ResourceAccessor.createResourceCategory(category, category);
                        }
                    }
                } catch (Exception e) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }

                Resource resource = new Resource(id, request.getParameter("resourceCategory"),
                        request.getParameter("resourceName"), request.getParameter("resourcePhone"),
                        request.getParameter("resourceAddress1"), request.getParameter("resourceAddress2"),
                        request.getParameter("resourceCity"), request.getParameter("resourceTerritory"),
                        request.getParameter("resourceCountry"), request.getParameter("resourcePostalCode"),
                        request.getParameter("resourceEmail"), request.getParameter("resourceDescription"));

                if (resource.isValid()) {
                    ///ResourceAccessor resourceDAO = new ResourceAccessor();
                    try {
                        //resourceDAO.createResource(resource);
                        ResourceAccessor.createResource(resource);// sets the Id to the one just made in the DB
                        for (String category : categoryArray) {
                            String temp = category.trim();
                            ResourceAccessor.createResourceCategoryResource(temp, resource.getResourceId());
                        }

                        session.removeAttribute("resourceBean");
                        request.getRequestDispatcher("Resources.jsp").forward(request, response);
                    } catch (Exception e) {
                        response.sendRedirect("ErrorPage.html");
                    }
                } else {
                    session.setAttribute("resourceBean", resource);
                    //request.getRequestDispatcher("resources/ResourceDetails.jsp").forward(request, response);
                    response.sendRedirect("resources/ResourceDetails.jsp");
                    //request.getRequestDispatcher(request.getContextPath() + "/resources/ResourceDetails.jsp").forward(request, response);
                }

                break;

            /**
             * Jessica Hoppe Resource Category List
             */
            case "list":
                ResourcePagBean resourcePageBean = new ResourcePagBean();
                try {
                    ArrayList<Resource> resourceList = ResourceAccessor.retreveResourceList();
                    resourcePageBean.setResourceList(resourceList);
                } catch (SQLException ex) {
                    resourcePageBean.setErrorMessage("Internal error: " + ex.getMessage());
                }

                request.setAttribute("pageBean", resourcePageBean);
                request.getRequestDispatcher("Resources.jsp").forward(request, response);

                break;

            /**
             * Christian Lopez
             */    
            case "details":
                id = 0;
                try {
                    id = Integer.parseInt(request.getParameter("resourceId"));
                } catch (Exception e) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }
                if (id == 0) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }
                Resource selectedResource = null;

                try {
                    selectedResource = ResourceAccessor.retrieveResourceById(id);
                } catch (Exception e) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }

                if (null == selectedResource) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }
                
                try {
                    selectedResource.setCategories(ResourceAccessor.retrieveCSVCategoriesByResourceId(selectedResource.getResourceId()));
                } catch (Exception e) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }

                session = request.getSession();

                session.setAttribute("resourceBean", selectedResource);

                //request.getRequestDispatcher("resources/ResourceDetails.jsp").forward(request, response);
                response.sendRedirect("resources/ResourceDetails.jsp");

                break;
            
            /**
             * Christian Lopez
             */
            case "update":
                id = 0;
                try {
                    id = Integer.parseInt(request.getParameter("resourceId"));
                } catch (Exception e) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }
                if (id == 0) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }
                Resource oldResource = null;

                try {
                    oldResource = ResourceAccessor.retrieveResourceById(id);
                } catch (Exception e) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }
                
                if (null == oldResource) {
                    response.sendRedirect("ErrorPage.html");
                    return;
                }
                
                Resource newResource = new Resource(id, request.getParameter("resourceCategory"),
                        request.getParameter("resourceName"), request.getParameter("resourcePhone"),
                        request.getParameter("resourceAddress1"), request.getParameter("resourceAddress2"),
                        request.getParameter("resourceCity"), request.getParameter("resourceTerritory"),
                        request.getParameter("resourceCountry"), request.getParameter("resourcePostalCode"),
                        request.getParameter("resourceEmail"), request.getParameter("resourceDescription"));
                
                if(newResource.isValid()) {
                    try {
                        if(ResourceAccessor.updateResourceProvider(oldResource, newResource)) {
                            request.getRequestDispatcher("Resources.jsp").forward(request, response);
                        }
                    } catch (Exception e) {
                        response.sendRedirect("ErrorPage.html");
                    }
                } else {
                    session.setAttribute("resourceBean", newResource);
                    response.sendRedirect("resources/ResourceDetails.jsp");
                }
                
                break;
                
            case "blank":
                session.removeAttribute("resourceBean");
                response.sendRedirect("resources/ResourceDetails.jsp");
                break;
                
//int resourceId, String categories, String name, String phone, String addressOne, String addressTwo, String city, String territory, String country, String postalCode, String email, String description
                
        }
    }

//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ResourceHandler</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ResourceHandler at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
                // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
                /**
                 * Handles the HTTP <code>GET</code> method.
                 *
                 * @param request servlet request
                 * @param response servlet response
                 * @throws ServletException if a servlet-specific error occurs
                 * @throws IOException if an I/O error occurs
                 */
                @Override
                protected void doGet
                (HttpServletRequest request,
                 HttpServletResponse response
                )
            throws ServletException
                , IOException  {
                    processRequest(request, response);
                }

                /**
                 * Handles the HTTP <code>POST</code> method.
                 *
                 * @param request servlet request
                 * @param response servlet response
                 * @throws ServletException if a servlet-specific error occurs
                 * @throws IOException if an I/O error occurs
                 */
                @Override
                protected void doPost
                (HttpServletRequest request,
                 HttpServletResponse response
                )
            throws ServletException
                , IOException  {
                    processRequest(request, response);
                }

                /**
                 * Returns a short description of the servlet.
                 *
                 * @return a String containing servlet description
                 */
                @Override
                public String getServletInfo
                 
                    () {
        return "Short description";
                }// </editor-fold>

        }
    

