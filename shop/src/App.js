import React, { useEffect, useState } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Col, Container, Navbar, Row} from 'react-bootstrap';
import RenderForm from './RenderForm';
import RenderTable from './RenderTable'
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

  const fetchData = async () => {
    const result = await axios.get(
        'http://localhost:4567/api/getitems'
    ).then((response) => {
    const json = JSON.parse(response.data);
    setStocks(json.stocks);
    })
  }

  useEffect(() => {
      fetchData();
  }, [setStocks]);

  const addItem = async (id, quantity) => {
    const result = await axios.post(
        'http://localhost:4567/api/setquantity', {
            'id': id,
            'quantity': quantity
        }
    ).then((response) => {
        console.log(response.data);
        const json = JSON.parse(response.data);
        fetchData();
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
      </Container>
    </div>
  );
}

export default App;