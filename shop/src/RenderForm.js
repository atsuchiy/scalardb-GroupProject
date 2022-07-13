import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import axios from 'axios';

const RenderForm = (props) => {
    const [id, setId] = useState(0);
    const [quantity, setQuantity] = useState(0);

    const handleIdChange = e => setId(e.target.value);
    const handleQuantityChange = e => setQuantity(e.target.value);

    const handleAdd = () => {
        props.addItem(id, quantity)
    }

    return (
        <>
            <Form className>
                <Form.Group controlId="formBasicId">
                <Form.Label>id</Form.Label>
                <Form.Control type="number" placeholder="Enter id" onChange={handleIdChange}/>
                </Form.Group>
                <Form.Group controlId="formBasicQuantity">
                <Form.Label>Quantity</Form.Label>
                <Form.Control type="number" placeholder="Enter quantity"  onChange={handleQuantityChange}/>
                </Form.Group>
            </Form>
            <Button variant="primary" type="submit" onClick={handleAdd}>
                Submit
            </Button>
        </>
    )
}

export default RenderForm