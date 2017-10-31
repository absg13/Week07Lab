/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import businesslogic.*;
import domainmodel.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 738377
 */
public class NoteServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NoteService ns = new NoteService();
        String action = request.getParameter("action");
        if (action != null && action.equals("view")) {
            int selectedId = Integer.parseInt(request.getParameter("selectedId"));
            try {
                Note note = ns.get(selectedId);
                request.setAttribute("selectedId", note);
            } catch (Exception ex) {
                Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        ArrayList<Note> notes = null;
        try {
            notes = (ArrayList<Note>) ns.getAll();
        } catch (Exception ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("notes", notes);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        int noteID = Integer.parseInt(request.getParameter("noteID"));
        String dateCreated = request.getParameter("dateCreated");
        String contents = request.getParameter("contents");
        
        NoteService ns = new NoteService();

        try {
            if (action.equals("delete")) {
                int selectedId = Integer.parseInt(request.getParameter("selectedId"));
                ns.delete(selectedId);
            } else if (action.equals("edit")) {
                ns.update(noteID, new Date(), contents);
            } else if (action.equals("add")) {
                ns.insert(noteID, new Date(), contents);
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Whoops.  Could not perform that action.");
        }
        
        ArrayList<Note> notes = null;
        try {
            notes = (ArrayList<Note>) ns.getAll();
        } catch (Exception ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("notes", notes);
        getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
    }
}
