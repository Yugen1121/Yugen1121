function advancedCounter() {
    count = 0;
    return {
        increment: () => {
            count ++;
            console.log(this.count);
        },
        decrement: () => count --,
        reset: () => count = 0,
        set: (val) => count = val,
        getCount: () => {return count},
    }
}