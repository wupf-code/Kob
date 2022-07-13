/*游戏基类*/

/*保存所有的游戏对象*/
const AC_GAME_OBJECTS=[];

export class AcGameObject{
    constructor() {
        AC_GAME_OBJECTS.push(this);
        this.timedelta=0;//两帧间时间间隔
        this.has_called_start=false; //判断是否为第一帧
    }
    //只执行一次，创建的时候执行
    start(){

    }
    //每一帧执行一次，除第一帧外
    update(){

    }
    //删除之前执行
    on_destroy(){

    }


    //删除对象
    destroy(){
        this.on_destroy()
        for(let i in AC_GAME_OBJECTS){  //in 在索引中循环
            const obj=AC_GAME_OBJECTS[i];
            if(obj===this){
                AC_GAME_OBJECTS.splice(i);
                break;
            }
        }
    }

}
let last_timestamp; //上一次执行时刻;
const step=(timestamp)=>{
    for(let obj of AC_GAME_OBJECTS){ //of 是在对象中循环
        if(!obj.has_called_start){
            obj.has_called_start=true;
            obj.start();
        }else{
            obj.timedelta=timestamp-last_timestamp;
            obj.update(); //画地图
        }
    }
    last_timestamp=timestamp;
    requestAnimationFrame(step)
}
/*在刷新前执行一次，浏览器默认每秒啥刷新60次*/
requestAnimationFrame(step)