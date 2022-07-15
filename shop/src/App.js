import React, { useEffect, useState } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Col, Container, Navbar, Row} from 'react-bootstrap';
import RenderForm from './RenderForm';
import {RenderTable, RenderOrderTable} from './RenderTable'
import axios from 'axios';

function Header() {
  return (
    <Navbar className='justify-content-center' bg='dark'>
      <h1 text='light'>Stock List</h1>
    </Navbar>
  )
}

const App = () => {
  const [stocks, setStocks] = useState([]);
  const [rakutenOrders, setRakutenOrders] = useState([]);
  const [amazonOrders, setAmazonOrders] = useState([]);

  const getItems = async () => {
    const result = await axios.get(
        'http://localhost:4567/api/getitems'
    ).then((response) => {
    const json = JSON.parse(response.data);
    setStocks(json.stocks);
    })
  }

  const getRakutenHistory = async () => {
    const result = await axios.get(
      'http://localhost:4567/api/getrakutenhistory'
    ).then((response) => {
      console.log("rakuten", response.data);
      const json = JSON.parse(response.data);
      setRakutenOrders(json.orders);
    });
  }

  const getAmazonHistory = async () => {
    const result = await axios.get(
      'http://localhost:4567/api/getamazonhistory'
    ).then((response) => {
      console.log("amazon", response.data);
      const json = JSON.parse(response.data);
      setAmazonOrders(json.orders);
    });
  }

  useEffect(() => {
      getItems();
      getRakutenHistory();
      getAmazonHistory();
  }, [setStocks, setRakutenOrders, setAmazonOrders]);

  const addItem = async (id, quantity) => {
    const result = await axios.post(
        'http://localhost:4567/api/setquantity', {
            'id': id,
            'quantity': quantity
        }
    ).then((response) => {
        console.log(response.data);
        const json = JSON.parse(response.data);
        getItems();
        alert('succeed in adding items!\n' + 'id' + ' : ' + id + '\n' + 'quantity' + ' : ' + quantity)
    });
  }

  return (
    <div className="App">
      <Header/>
      <Container>
        <Row>
          <Col xs={12} md={8}>
            <h2>Stock List Table</h2>
            <RenderTable stocks={stocks}/>
          </Col>
          <Col xs={4}>
            <h2>Add items</h2>
            <RenderForm addItem={addItem}/>
          </Col>
        </Row>
        <Row>
          <Col lg={12} xl={6}>
            <h2>Rakuten order history</h2>
            <RenderOrderTable orders={rakutenOrders}/>
          </Col>
          <Col lg={12} xl={6}>
            <h2>Amazon order history</h2>
            <RenderOrderTable orders={amazonOrders}/>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default App;