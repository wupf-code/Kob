/**
 *
 * 蛇的身体，每一节身体视为一个cell
 *
 * */
export  class Cell{
    constructor(r,c){
        this.r=r;
        this.c=c;
        this.x=c+0.5;
        this.y=r+0.5;
    }


}