import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Button, Col, Container, Navbar, Row} from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import RenderTable from './RenderTable'

function Header() {
  return (
    <Navbar className='justify-content-center' bg='dark'>
      <h1 text='light'>Stock List</h1>
    </Navbar>
  )
}

// submitしたらPOST
// 結果をstateに入れる
function RenderForm() {
  return (
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
  )
}

function App() {
  return (
    <div className="App">
      <Header/>
      <Container>
        <Row>
          <Col xs={12} md={8}>
            <h2>Stock List Table</h2>
            <RenderTable/>
          </Col>
          <Col xs={4}>
            <h2>Add items</h2>
            <RenderForm/>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default App;