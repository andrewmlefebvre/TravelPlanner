import './BlurCard.css';

function Card(props){
    return (<div className='card'>
        <div className='blur-bgimage'>{props.children}</div>
        </div>
        );
}
export default Card;