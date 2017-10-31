/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import domainmodel.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 738377
 */
public class NotesDB {

    public int insert(Note note) throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try {
            String preparedQuery = "INSERT INTO Notes (noteId,dateCreated,contents) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setInt(1, note.getNoteID());
            ps.setDate(2, (Date) note.getDateCreated());
            ps.setString(3, note.getContents());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(NotesDB.class.getName()).log(Level.SEVERE, "Cannot insert " + note.toString(), ex);
            throw new NotesDBException("Error inserting note");
        } finally {
            pool.freeConnection(connection);
        }
    }

    public int update(Note note) throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try {
            String preparedSQL = "UPDATE Notes SET "
                    + "noteId = ?, "
                    + "dateCreated = ?, "
                    + "contents = ?, ";

            PreparedStatement ps = connection.prepareStatement(preparedSQL);

            ps.setInt(1, note.getNoteID());
            ps.setDate(2, (Date) note.getDateCreated());
            ps.setString(3, note.getContents());

            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(NotesDB.class.getName()).log(Level.SEVERE, "Cannot update " + note.toString(), ex);
            throw new NotesDBException("Error updating note");
        } finally {
            pool.freeConnection(connection);
        }
    }

    public List<Note> getAll() throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM notes;");
            rs = ps.executeQuery();
            List<Note> notes = new ArrayList<>();
            while (rs.next()) {
                notes.add(new Note(rs.getInt("noteID"), rs.getDate("dateCreated"), rs.getString("contents")));
            }
            pool.freeConnection(connection);
            return notes;
        } catch (SQLException ex) {
            Logger.getLogger(NotesDB.class.getName()).log(Level.SEVERE, "Cannot read notes", ex);
            throw new NotesDBException("Error getting Notes");
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
            }
            pool.freeConnection(connection);
        }
    }
    
    public Note getNote(int noteId) throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        String selectSQL = "SELECT * FROM Notes WHERE noteId = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(selectSQL);
            ps.setInt(1, noteId);
            rs = ps.executeQuery();

            Note note = null;
            while (rs.next()) {
                note = new Note(rs.getInt("noteID"), rs.getDate("dateCreated"), rs.getString("contents"));
            }
            pool.freeConnection(connection);
            return note;
        } catch (SQLException ex) {
            Logger.getLogger(NotesDB.class.getName()).log(Level.SEVERE, "Cannot read notes", ex);
            throw new NotesDBException("Error getting Notes");
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
            }
            pool.freeConnection(connection);
        }
    }
    
    public int delete(Note note) throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        String preparedQuery = "DELETE FROM Notes WHERE noteId = ?";
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement(preparedQuery);
            ps.setInt(1, note.getNoteID());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(NotesDB.class.getName()).log(Level.SEVERE, "Cannot delete " + note.toString(), ex);
            throw new NotesDBException("Error deleting Note");
        } finally {
            pool.freeConnection(connection);
        }
    }
}
