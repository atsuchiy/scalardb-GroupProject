import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Table from 'react-bootstrap/Table';
import axios from 'axios';

const RenderTable = () => {
    const [stocks, setStocks] = useState([])

    useEffect(async () => {
        const fetchData = async () => {
            fetch('http://localhost:4567/api/getitems', {
                method: 'GET',
                mode: 'cors',
            })
            .then(res => res.json())
            .then(data => {
                setStocks(data.stocks)
            })
        }
        fetchData();
    }, [])

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
                {stocks.map(item =>
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