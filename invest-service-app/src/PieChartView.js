import React from 'react';
import { PieChart } from 'react-minimal-pie-chart';
import { round } from "./service/commonService"

const divStyle = {
    
}

const defaultLabelStyle = {
    fontSize: '5px',
    fontFamily: 'sans-serif',
};

const shiftSize = 7;

export const PieChartView = (props) => {

    const data = [
        { title: 'Actual', value: props.actual, color: '#F15025' },
        { title: 'Target', value: round(props.target - props.actual, 2), color: '#E6E8E6' }
    ]

    return <div style={divStyle}>
        <PieChart
            data={data}
            lineWidth={20}
            paddingAngle={18}
            rounded
            label={({ dataEntry }) => dataEntry.value}
            labelStyle={(index) => ({
                fill: data[index].color,
                fontSize: '5px',
                fontFamily: 'sans-serif',
            })}
            labelPosition={60}
            radius={PieChart.defaultProps.radius - shiftSize}
        />
    </div >
}
