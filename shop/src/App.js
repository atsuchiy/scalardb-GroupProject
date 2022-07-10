import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Button, Col, Container, Row, Table} from 'react-bootstrap';
import Form from 'react-bootstrap/Form';

function RenderTable() {
  return (
    <>
    <h2>Stocl List Table</h2>
      <Table>
        <thead>
          <tr>
            <td>item_id</td>
            <td>name</td>
            <td>quantity</td>
          </tr>
        </thead>
      </Table>
    </>
  )
}

function RenderForm() {
  return (
    <>
      <h2>Add items</h2>
      <Form className>
        <Form.Group controlId="formBasicId">
          <Form.Label>id</Form.Label>
          <Form.Control type="email" placeholder="Enter id"/>
        </Form.Group>
        <Form.Group controlId="formBasicQuantity">
          <Form.Label>Quantity</Form.Label>
          <Form.Control type="password" placeholder="Enter quantity"/>
        </Form.Group>
        <Button variant="primary" type="submit">
          Submit
        </Button>
      </Form>
    </>
  )
}

function App() {
  return (
    <div className="App">
      <h1>Stock List</h1>
      <Container>
        <Row>
          <Col xs={12} md={8}>
            <RenderTable/>
          </Col>
          <Col xs={4}>
            <RenderForm/>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default App;