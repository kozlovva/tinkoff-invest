import {round } from "./commonService"

export const calcChanges = (oldData, serverResponse) => {
    var result = {};
    result.freeMoney = {
        value: serverResponse.freeMoney,
        changes: round(serverResponse.freeMoney - oldData.freeMoney.value, 2)
    };
    result.activeMoney = {
        value: serverResponse.activeMoney,
        changes: round(serverResponse.activeMoney - oldData.activeMoney.value, 2)
    };
    result.total = {
        value: serverResponse.total,
        changes: round(serverResponse.total - oldData.total.value, 2)
    };
    result.date = serverResponse.date;
    console.log("Old", oldData)
    console.log("New", serverResponse)
    console.log("Result", result)
    return result;
}