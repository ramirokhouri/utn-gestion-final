package edu.utn.gestion.service;

import edu.utn.gestion.dao.OrderDAO;
import edu.utn.gestion.exception.DataAccessException;
import edu.utn.gestion.exception.GestionAppException;
import edu.utn.gestion.model.Book;
import edu.utn.gestion.model.Order;
import edu.utn.gestion.model.OrderDetail;
import edu.utn.gestion.service.generic.GenericService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 26/12/15.
 */
public class OrderService extends GenericService<Order, Long> {
    private static final OrderService INSTANCE = new OrderService();
    private static final int DEFAULT_QUANTITY_TO_ORDER = 10;

    /**
     * Creates a new instance of <code>OrderService</code>.
     */
    private OrderService() {
        super(OrderDAO.getInstance());
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Order> findBySearch(String searchString) throws GestionAppException {
        try {
            return this.genericDAO.findObjectsBySearch(searchString);
        } catch (DataAccessException ex) {
            throw new GestionAppException(ex);
        }
    }

    /**
     * Returns a list with the details for the new order.
     *
     * @return A list with the details for the new order. Returns an empty list if there is no books for order.
     * @throws GestionAppException if something goes wrong.
     */
    public List<OrderDetail> getBooksForNewOrder() throws GestionAppException {
        List<Book> bookList = BookService.getInstance().findBooksWithMinStock();
        List<OrderDetail> orderDetailList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(bookList)) {
            for (Book book : bookList) {
                orderDetailList.add(new OrderDetail(book, DEFAULT_QUANTITY_TO_ORDER));
            }
        }

        return orderDetailList;
    }
}
