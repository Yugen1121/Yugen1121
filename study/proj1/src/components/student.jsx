import PropTypes from 'prop-types';




function Student(props){
    return (
        <>
        <div className="student">
            <div>Name: {props.name}</div>
            <div>Roll No: {props.rollno}</div>
            <div>Is A student: {props.isAStudent? "Yes": "No"}</div>
        </div>
        </>
    )
}

Student.propTypes = {
    name: PropTypes.string,
    rollno: PropTypes.number,
    isAStudent: PropTypes.bool,

}

Student.defaultProps = {
    name: "Guest",
    rollno: 0,
    isAStudent: false,
}


export default Student;