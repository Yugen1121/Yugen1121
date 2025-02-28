function shapes(shape){
    this.shape = shape;
}

shapes.prototype.area = function(){
    console.log("This does not have area!")
}

function rectangle(width, height){
    shapes.call(this, "rectangle");
    this.name = "rectangle";
    this.width = width;
    this.height = height
}

rectangle.prototype = Object.create(shapes.prototype);
rectangle.constructor = rectangle;
rectangle.prototype.area = function(){
    console.log(this.height * this.width);

}

function square(length){
    rectangle.call(this, "square");
    this.name = "square";
    this.length = length
}

square.prototype = Object.create(rectangle);
square.prototype = function(){
    console.log(length*length)
}

x = new shapes("hell0");
x.area();

y =  new rectangle(2, 5);
y.area();