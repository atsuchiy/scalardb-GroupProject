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

export default RenderTable