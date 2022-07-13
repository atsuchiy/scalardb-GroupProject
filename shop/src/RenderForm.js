import React, { Component } from 'react';

export default class RenderForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: '',
            quantity: 0
        };
    }

    setId(e) {
        this.setState({id: e.target.value});
    }

    setQuantity(e) {
        this.setState({quantity: e.target.value});
    }

    addItem() {
        fetch('http://localhost:3001/todos', {
            method: 'POST',
            body: JSON.stringify({
                id: this.state.id,
                quantity: this.state.quantity
            }),
            headers: new Headers({ 'Content-type' : 'application/json' })
        }).then( () => {
            // リストの更新
            this.fetchResponse();
            // 値の初期化
            this.refs.newText.value = "";
        })
    }

    render() {
        const id = this.props.id;
        const quantity = this.props.quantity;
        return (
            <>
                <h2>Add items</h2>
                <Form className>
                    <Form.Group controlId="formBasicId">
                    <Form.Label>id</Form.Label>
                    <Form.Control type="email" placeholder="Enter id" onChange={this.setId}/>
                    </Form.Group>
                    <Form.Group controlId="formBasicQuantity">
                    <Form.Label>Quantity</Form.Label>
                    <Form.Control type="password" placeholder="Enter quantity"  onChange={this.setQuantity}/>
                    </Form.Group>
                    <Button variant="primary" type="submit" onClick={this.addItem}>
                    Submit
                    </Button>
                </Form>
            </>
        )
    }
}