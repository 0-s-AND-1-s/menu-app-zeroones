package com.abhi.repository.custom;

import CustomizedExceptions.ReceiptNotFoundException;
import com.abhi.dto.projection.ReceiptDetailsProjection;
import com.abhi.dto.response.ReceiptItemDetails;
import com.abhi.model.QOrder;
import com.abhi.model.QOrderItem;
import com.abhi.model.QReceipt;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderReceiptRepositoryCustomImpl implements OrderReceiptRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public OrderReceiptRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    @Transactional
    public ReceiptDetailsProjection getReceiptDetails(Integer receiptId) {
        QOrder o = QOrder.order;
        QReceipt r = QReceipt.receipt;
        QOrderItem oi = QOrderItem.orderItem;
        /*
         * This query joins Receipt -> Order -> OrderItems and returns one row
         * for each OrderItem associated with the Order. Since a Receipt belongs
         * to a single Order, but an Order can contain multiple OrderItems, the
         * receipt-level and order-level fields (subTotal, salesTax, total,
         * orderId, orderTime) are repeated in every row of the result set.
         *
         * The only fields that vary across rows are the OrderItem details
         * (itemName, quantity, unitPrice). Therefore, receipt and order
         * information should be extracted from the first row, while all rows
         * should be iterated to collect the complete list of item details.
         */
        List<Tuple> tupleList = jpaQueryFactory.select(
                        r.subTotal,
                        r.salesTax,
                        r.total,

                        o.orderId,
                        o.orderTime,

                        oi.itemName,
                        oi.quantity,
                        oi.unitPrice).
                from(r).
                join(r.order, o).
                join(o.orderItems, oi).
                where(r.receiptId.eq(receiptId)).
                fetch();
        if (tupleList.isEmpty())
            throw new ReceiptNotFoundException("Receipt not available for receipt id::" + receiptId);
//Receipt -> OrderItems
        Tuple firstTuple = tupleList.get(0);
        List<ReceiptItemDetails> receiptItemDetails = tupleList.stream().map((tuple) -> {
            ReceiptItemDetails receiptItemDetail = new ReceiptItemDetails();
            receiptItemDetail.setItemName(tuple.get(oi.itemName));
            receiptItemDetail.setQuantity(tuple.get(oi.quantity));
            receiptItemDetail.setUnitPrice(tuple.get(oi.unitPrice));
            return receiptItemDetail;
        }).toList();
        return new ReceiptDetailsProjection(firstTuple.get(r.subTotal), firstTuple.get(r.salesTax), firstTuple.get(r.total),
                firstTuple.get(o.orderId), firstTuple.get(o.orderTime), receiptItemDetails);

    }

}


