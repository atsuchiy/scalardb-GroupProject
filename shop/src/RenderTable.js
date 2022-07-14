import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Table from 'react-bootstrap/Table';

const RenderTable = (props) => {
    return(
        <Table>
            <thead>
                <tr>
                    <td>item_id</td>
                    <td>name</td>
                    <td>quantity</td>
                </tr>
            </thead>
            <tbody>
                {props.stocks.map(item =>
                    <tr>
                        <td>{item.id}</td>
                        <td>{item.name}</td>
                        <td>{item.quantity}</td>
                    </tr>
                )}
            </tbody>
        </Table>
    )
}

const RenderOrderTable = (props) => {
    return(
        <Table>
            <thead>
                <tr>
                    <td>order_id</td>
                    <td>item_id</td>
                    <td>item_name</td>
                    <td>quantity</td>
                    <td>timestamp</td>
                </tr>
            </thead>
            <tbody>
                {props.orders.map(order =>
                    <tr>
                        <td>{order.order_id}</td>
                        <td>{order.item_id}</td>
                        <td>{order.name}</td>
                        <td>{order.quantity}</td>
                        <td>{order.timestamp}</td>
                    </tr>
                )}
            </tbody>
        </Table>
    )
}

export {RenderTable, RenderOrderTable}