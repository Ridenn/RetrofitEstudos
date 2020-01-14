'use strict'

const mongoose = require('mongoose')
const Product = mongoose.model('Product')
const validationContract = require('../validators/fluent-validator')
const repository = require('../repositories/product_repository')


exports.get = async(req, res, next) => {
    try{
    var data = await repository.get()
        repository.get()
        res.status(200).send(data)
    } catch(e){
        res.status(500).send({
            message: 'falha ao processar sua requisição'
        })
    }
}

exports.post = async (req, res, next) =>{
    let contract = new validationContract()
    contract.hasMinLen(req.body.title,3, 'O titulo de conter pelo menos 3 caracteres')

    if(!contract.isValid()){
        res.status(400).send(contract.errors()).end()
        return
    }
    try{
        var data = await repository.create(req.body)
        res.status(201).send(req.body)
    }catch(e){
        res.status(400).send({message: 'Falha ao cadastrar produto', data : e})
    }
}

exports.put = async(req, res, next) => {
    try {
       let data =  await repository.update(req.params.id, req.body);
        res.status(200).send(req.body);
    } catch (e) {
        res.status(500).send({
            message: 'Falha ao processar sua requisição' + e
        });
    }
};

exports.delete = async(req, res, next) => {
    try {
        await repository.delete(req.params.id)
        res.status(200).send({
            message: 'Produto removido com sucesso!'
        });
    } catch (e) {
        res.status(500).send({
            message: 'Falha ao processar sua requisição'
        });
    }
};