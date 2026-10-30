package ua.edu.nuos.lab3_jakartaeedb;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ua.edu.nuos.lab3_jakartaeedb.dao.CrudDAO;
import ua.edu.nuos.lab3_jakartaeedb.model.Watch;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;

@WebServlet(name = "WatchServlet", value = "/watches")
public class WatchServlet extends HttpServlet {
    @EJB
    private CrudDAO<Watch> watchDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("find".equals(action)) {
            try {
                long id = Long.parseLong(req.getParameter("id"));
                Watch watch = watchDao.findById(id);

                if (watch != null) {
                    req.setAttribute("watches", Collections.singletonList(watch));
                } else {
                    req.setAttribute("watches", Collections.emptyList());
                    req.setAttribute("message", "Годинник з ID " + id + " не знайдено!");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Некоректний ID для пошуку!");
                req.setAttribute("watches", watchDao.findAll());
            }
        } else if ("edit".equals(action)) {
            try {
                long id = Long.parseLong(req.getParameter("id"));
                Watch watch = watchDao.findById(id);

                if (watch != null) {
                    req.setAttribute("watch", watch);
                } else {
                    req.setAttribute("message", "Годинник з ID " + id + " не знайдено!");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Некоректний ID!");
            }
        } else {
            req.setAttribute("watches", watchDao.findAll());
        }

        String message = (String) req.getSession().getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            req.getSession().removeAttribute("message");
        }

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("save".equals(action)) {
            String watchIdParam = req.getParameter("watchId");
            Watch watch;
            if (watchIdParam == null || watchIdParam.isBlank()) {
                watch = new Watch();
            } else {
                long watchId = Long.parseLong(watchIdParam);
                watch = watchDao.findById(watchId);

                if (watch == null) {
                    req.getSession().setAttribute("message", "Годинник для редагування не знайдено!");
                    resp.sendRedirect(req.getContextPath() + "/watches");
                    return;
                }
            }

            watch.setBrand(req.getParameter("brand"));
            watch.setType(req.getParameter("type"));
            watch.setReleaseDate(Date.valueOf(req.getParameter("releaseDate")).toLocalDate());
            watch.setPrice(Double.parseDouble(req.getParameter("price")));
            watch.setCaseDiameter(Double.parseDouble(req.getParameter("caseDiameter")));
            watch.setQuantity(Integer.parseInt(req.getParameter("quantity")));
            watch.setWarrantyMonths(Integer.parseInt(req.getParameter("warrantyMonths")));
            watch.setManufacturerId(Long.parseLong(req.getParameter("manufacturerId")));

            if (watchIdParam == null || watchIdParam.isBlank()) {
                watchDao.create(watch);
                req.getSession().setAttribute("message", "Годинник " + watch.getBrand() + " успішно додано!");
            } else {
                watchDao.update(watch);
                req.getSession().setAttribute("message", "Дані годинника успішно оновлено!");
            }

        } else if ("delete".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            Watch watch = watchDao.findById(id);
            if (watch != null) {
                watchDao.delete(id);
                req.getSession().setAttribute("message", "Годинник з ID " + id + " успішно видалено!");
            } else {
                req.getSession().setAttribute("message", "Годинник з ID " + id + " не знайдено!");
            }
        }

        resp.sendRedirect(req.getContextPath() + "/watches");
    }
}