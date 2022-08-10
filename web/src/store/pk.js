
export default {
    state: {
        status : "matching" ,//matching表示匹配页面，playing表示游戏页面
        socket : null,
        opponent_username : "",
        opponent_photo : "",
        gamemap:"",
    },
    getters: {
    },
    mutations: {
        updateSocket(state, socket){
            state.socket = socket;
        },
        updateOpponent(state, opponent){
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateGamemap(state, gamemap) {
            state.gamemap = gamemap;
        }
    },
    actions: {
    },
    modules: {
    }
}
