'use strict'

const mongoose = require('mongoose')
const Product = mongoose.model('Product')

exports.get = async() => { 
    const res = await Product.find({})
    return res
}

exports.create = async (data) => {
   var product = new Product(data)
    await product
     .save()
}

exports.update = async (id, data) => {
    var options = {new :true}
    return await Product
        .findByIdAndUpdate(id,{
            $set:{
                title: data.title,
                description: data.description,
                price: data.price,
                image: data.image
            }
        })
}

exports.delete = async(id) => {
   return  await Product
        .findByIdAndDelete(id);
}